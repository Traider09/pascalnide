package com.duy.pascal.backend.exceptions;

import com.js.interpreter.ast.returnsvalue.ReturnsValue;

public class NonIntegerIndexException extends com.duy.pascal.backend.exceptions.ParsingException {

    public ReturnsValue value;

    public NonIntegerIndexException(ReturnsValue value) {
        super(value.getLine());
        this.value = value;
    }

    @Override
    public String getMessage() {
        return "Array indexes must be integers: " + value;
    }
}
