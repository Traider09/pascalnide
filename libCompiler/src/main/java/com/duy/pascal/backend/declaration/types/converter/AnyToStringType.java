package com.duy.pascal.backend.declaration.types.converter;

import android.support.annotation.NonNull;

import com.duy.pascal.backend.ast.codeunit.RuntimeExecutableCodeUnit;
import com.duy.pascal.backend.ast.expressioncontext.CompileTimeContext;
import com.duy.pascal.backend.ast.expressioncontext.ExpressionContext;
import com.duy.pascal.backend.ast.variablecontext.VariableContext;
import com.duy.pascal.backend.ast.runtime_value.value.AssignableValue;
import com.duy.pascal.backend.ast.runtime_value.value.NullValue;
import com.duy.pascal.backend.ast.runtime_value.value.RuntimeValue;
import com.duy.pascal.backend.linenumber.LineInfo;
import com.duy.pascal.backend.parse_exception.ParsingException;
import com.duy.pascal.backend.runtime_exception.RuntimePascalException;
import com.duy.pascal.backend.declaration.types.BasicType;
import com.duy.pascal.backend.declaration.types.RuntimeType;

public class AnyToStringType implements RuntimeValue {
    private RuntimeValue value;

    public AnyToStringType(RuntimeValue value) {
        this.value = value;
    }

    @NonNull
    @Override
    public Object getValue(VariableContext f, RuntimeExecutableCodeUnit<?> main)
            throws RuntimePascalException {
        Object value = this.value.getValue(f, main);
        if (value instanceof NullValue) {
            return value;
        }
        return value.toString();
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public RuntimeType getType(ExpressionContext f)
            throws ParsingException {
        return new RuntimeType(BasicType.create(String.class), false);
    }

    @NonNull
    @Override
    public LineInfo getLineNumber() {
        return value.getLineNumber();
    }

    @Override
    public void setLineNumber(LineInfo lineNumber) {

    }

    @Override
    public Object compileTimeValue(CompileTimeContext context)
            throws ParsingException {
        Object o = value.compileTimeValue(context);
        if (o != null) {
            return o.toString();
        } else {
            return null;
        }
    }

    @Override
    public RuntimeValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return new AnyToStringType(value.compileTimeExpressionFold(context));
    }

    @Override
    public AssignableValue asAssignableValue(ExpressionContext f) {
        return null;
    }
}