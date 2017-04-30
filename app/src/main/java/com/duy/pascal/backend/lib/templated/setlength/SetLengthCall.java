/*
 *  Copyright 2017 Tran Le Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duy.pascal.backend.lib.templated.setlength;

import com.duy.pascal.backend.exceptions.ParsingException;
import com.duy.pascal.backend.exceptions.UnAssignableTypeException;
import com.duy.pascal.backend.linenumber.LineInfo;
import com.duy.pascal.backend.pascaltypes.DeclaredType;
import com.duy.pascal.backend.pascaltypes.RuntimeType;
import com.js.interpreter.ast.expressioncontext.CompileTimeContext;
import com.js.interpreter.ast.expressioncontext.ExpressionContext;
import com.js.interpreter.ast.instructions.Executable;
import com.js.interpreter.ast.instructions.SetValueExecutable;
import com.js.interpreter.ast.returnsvalue.FunctionCall;
import com.js.interpreter.ast.returnsvalue.ReturnsValue;
import com.js.interpreter.runtime.VariableBoxer;
import com.js.interpreter.runtime.VariableContext;
import com.js.interpreter.runtime.codeunit.RuntimeExecutable;
import com.js.interpreter.runtime.exception.RuntimePascalException;

import java.lang.reflect.Array;

public class SetLengthCall extends FunctionCall {

    ReturnsValue array;
    ReturnsValue size;
    DeclaredType elemtype;

    LineInfo line;

    public SetLengthCall(ReturnsValue array, ReturnsValue size, DeclaredType elemType, LineInfo line) {
        this.array = array;
        this.size = size;
        this.elemtype = elemType;
        this.line = line;
    }


    @Override
    public RuntimeType getType(ExpressionContext f) throws ParsingException {
        return null;
    }

    @Override
    public LineInfo getLine() {
        return line;
    }

    @Override
    public SetValueExecutable createSetValueInstruction(ReturnsValue r)
            throws UnAssignableTypeException {
        throw new UnAssignableTypeException(this);
    }

    @Override
    public Object compileTimeValue(CompileTimeContext context) {
        return null;
    }

    @Override
    public ReturnsValue compileTimeExpressionFold(CompileTimeContext context)
            throws ParsingException {
        return new SetLengthCall(array.compileTimeExpressionFold(context),
                size.compileTimeExpressionFold(context), elemtype, line);
    }

    @Override
    public Executable compileTimeConstantTransform(CompileTimeContext c)
            throws ParsingException {
        return new SetLengthCall(array.compileTimeExpressionFold(c),
                size.compileTimeExpressionFold(c), elemtype, line);
    }

    @Override
    protected String getFunctionName() {
        return "setlength";
    }

    @Override
    public Object getValueImpl(VariableContext f, RuntimeExecutable<?> main)
            throws RuntimePascalException {
        int length = (Integer) size.getValue(f, main);
        @SuppressWarnings("rawtypes")
        VariableBoxer a = (VariableBoxer) array.getValue(f, main);
        Object arr = a.get();
        int oldlength = Array.getLength(arr);
        Object newarr = Array.newInstance(elemtype.getTransferClass(), length);
        if (oldlength > length) {
            System.arraycopy(arr, 0, newarr, 0, length);
        } else {
            System.arraycopy(arr, 0, newarr, 0, oldlength);
            for (int i = oldlength; i < length; i++) {
                Array.set(newarr, i, elemtype.initialize());
            }
        }
        a.set(newarr);
        return null;
    }
}