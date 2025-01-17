package com.jewel.ergon.jobs.services.eql;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {
    private static final Pattern CONDITION_PATTERN = Pattern.compile("(\\w+)\\s*(=|!=|<|>|<=|>=|LIKE)\\s*\"([^\"]+)\"");
    private static final Pattern LOGICAL_OPERATOR_PATTERN = Pattern.compile("\\s+(?i)(AND|OR)\\s+");

    public static List<FilterCriteria> parse(String query) {
        List<FilterCriteria> criteriaList = new ArrayList<>();

        // Split query by logical operators
        String[] conditions = query.split(LOGICAL_OPERATOR_PATTERN.pattern());
        Matcher logicalMatcher = LOGICAL_OPERATOR_PATTERN.matcher(query);

        for (int i = 0; i < conditions.length; i++) {
            Matcher conditionMatcher = CONDITION_PATTERN.matcher(conditions[i].trim());
            if (conditionMatcher.find()) {
                FilterCriteria criteria = new FilterCriteria(
                        conditionMatcher.group(1),     // Field name
                        conditionMatcher.group(2),     // Operator
                        conditionMatcher.group(3),     // Value
                        (i > 0 && logicalMatcher.find()) ? logicalMatcher.group(1) : null // Logical operator
                );
                criteriaList.add(criteria);
            }
        }

        return criteriaList;
    }
}
