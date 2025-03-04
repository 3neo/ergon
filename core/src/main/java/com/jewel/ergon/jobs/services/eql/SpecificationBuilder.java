package com.jewel.ergon.jobs.services.eql;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate buildCondition(FilterCriteria filter, CriteriaBuilder cb, Root<T> root) {
        Path<?> path = root.get(filter.getKey());
        Class<?> fieldType = path.getJavaType();

        Object value = filter.getValue();
        if (fieldType == Boolean.class && value instanceof String) {
            value = Boolean.parseBoolean((String) value);
        }

        if (fieldType == LocalDate.class && value instanceof String) {
            value = LocalDate.parse((String) value)   ;
        }

        switch (filter.getOperator()) {
            case "=":
                return cb.equal(path, value);
            case "!=":
                return cb.notEqual(path, value);
            case "<":
                return cb.lessThan(path.as((Class<? extends Comparable>) fieldType), (Comparable) value);
            case ">":
                return cb.greaterThan(path.as((Class<? extends Comparable>) fieldType), (Comparable) value);
            case "<=":
                return cb.lessThanOrEqualTo(path.as((Class<? extends Comparable>) fieldType), (Comparable) value);
            case ">=":
                return cb.greaterThanOrEqualTo(path.as((Class<? extends Comparable>) fieldType), (Comparable) value);
            case "like":
                if (fieldType == String.class) {
                    return cb.like(path.as(String.class), "%" + value + "%");
                } else {
                    throw new UnsupportedOperationException("LIKE operator is only supported for String fields");
                }
            default:
                throw new UnsupportedOperationException("Operator not supported: " + filter.getOperator());
        }
    }
}
