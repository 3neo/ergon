package com.jewel.ergon.jobs.services.eql;

import com.jewel.ergon.jobs.services.CompanyService;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SpecificationBuilder<T> {




    public Specification<T> buildSpecification(List<FilterCriteria> filters) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            for (FilterCriteria filter : filters) {
                Predicate condition = buildCondition(filter, criteriaBuilder, root);

                // Apply logical operators
                if ("OR".equalsIgnoreCase(filter.getLogicalOp())) {
                    predicate = criteriaBuilder.or(predicate, condition);
                } else {
                    predicate = criteriaBuilder.and(predicate, condition);
                }
            }

            return predicate;
        };
    }

    private Predicate buildCondition(FilterCriteria filter, CriteriaBuilder cb, Root<T> root) {
        switch (filter.getOperator()) {
            case "=":
                return cb.equal(root.get(filter.getKey()), filter.getValue());
            case "!=":
                return cb.notEqual(root.get(filter.getKey()), filter.getValue());
            case ">":
                return cb.greaterThan(root.get(filter.getKey()), (Comparable) filter.getValue());
            case "<":
                return cb.lessThan(root.get(filter.getKey()), (Comparable) filter.getValue());
            case ">=":
                return cb.greaterThanOrEqualTo(root.get(filter.getKey()), (Comparable) filter.getValue());
            case "<=":
                return cb.lessThanOrEqualTo(root.get(filter.getKey()), (Comparable) filter.getValue());
            case "LIKE":
                return cb.like(root.get(filter.getKey()), "%" + filter.getValue() + "%");
            default:
                throw new UnsupportedOperationException("Operator not supported: " + filter.getOperator());
        }
    }
}
