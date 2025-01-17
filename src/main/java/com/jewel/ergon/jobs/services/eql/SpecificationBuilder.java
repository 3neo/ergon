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
        Path<?> path = root.get(filter.getKey());
        Class<?> fieldType = path.getJavaType();

        Object value = filter.getValue();
        if (fieldType == Boolean.class && value instanceof String) {
            value = Boolean.parseBoolean((String) value);
        }

        switch (filter.getOperator()) {
            case "=":
                return cb.equal(path, value);
            case "!=":
                return cb.notEqual(path, value);
            case "<":
                return cb.lessThan(path.as(Comparable.class), (Comparable) value);
            case ">":
                return cb.greaterThan(path.as(Comparable.class), (Comparable) value);
            case "<=":
                return cb.lessThanOrEqualTo(path.as(Comparable.class), (Comparable) value);
            case ">=":
                return cb.greaterThanOrEqualTo(path.as(Comparable.class), (Comparable) value);
            case "LIKE":
                return cb.like(path.as(String.class), "%" + value + "%");
            default:
                throw new UnsupportedOperationException("Operator not supported: " + filter.getOperator());
        }
    }
}
