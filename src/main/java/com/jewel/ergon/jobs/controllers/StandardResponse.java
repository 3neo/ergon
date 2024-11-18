package com.jewel.ergon.jobs.controllers;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StandardResponse<T> {
    private final int statusCode;
    private final String message;
    private final T data;
}
