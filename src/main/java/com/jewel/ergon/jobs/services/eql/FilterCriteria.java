package com.jewel.ergon.jobs.services.eql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterCriteria {
    private String key;        // Field name
    private String operator;   // e.g., '=', '!=', 'LIKE'
    private Object value;      // Filter value
    private String logicalOp;  // AND/OR (optional, null for first criteria)
}
