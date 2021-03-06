package com.duy.pascal.backend.ast.runtime_value.value;

import android.support.annotation.NonNull;

import com.duy.pascal.backend.ast.codeunit.RuntimeExecutableCodeUnit;
import com.duy.pascal.backend.ast.expressioncontext.CompileTimeContext;
import com.duy.pascal.backend.ast.expressioncontext.ExpressionContext;
import com.duy.pascal.backend.ast.variablecontext.VariableContext;
import com.duy.pascal.backend.linenumber.LineInfo;
import com.duy.pascal.backend.parse_exception.ParsingException;
import com.duy.pascal.backend.runtime_exception.RuntimePascalException;
import com.duy.pascal.backend.declaration.lang.types.RuntimeType;

public class CachedReturnValue implements RuntimeValue {
    private RuntimeValue other;
    private Object cache = null;

    public CachedReturnValue(RuntimeValue other) {
        this.other = other;
    }

    @Override
    public RuntimeType getRuntimeType(ExpressionContext f) throws ParsingException {
        return other.getRuntimeType(f);
    }

    @NonNull
    @Override
    public LineInfo getLineNumber() {
        return other.getLineNumber();
    }

    @Override
    public void setLineNumber(LineInfo lineNumber) {

    }

    @Override
    public Object compileTimeValue(CompileTimeContext context)
            throws ParsingException {
        cache = other.compileTimeValue(context);
        return cache;
    }

    @Override
    public RuntimeValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return new CachedReturnValue(other);
    }

    @Override
    public AssignableValue asAssignableValue(ExpressionContext f) {
        return null;
    }

    @NonNull
    @Override
    public Object getValue(VariableContext f, RuntimeExecutableCodeUnit<?> main)
            throws RuntimePascalException {
        cache = other.getValue(f, main);
        return cache;
    }

}
