package com.duy.pascal.backend.exceptions;


import com.duy.pascal.backend.linenumber.LineInfo;

public class UnrecognizedTypeException extends ParsingException {
    public String type;

    public UnrecognizedTypeException(LineInfo line, String name) {
        super(line, "Type " + name + " is not defined");
        type = name;
    }

}
