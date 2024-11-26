package com.github.tadukoo.java.parsing;

import com.github.tadukoo.java.JavaCodeType;

/**
 * Used as a pojo for a return type of the various parsing sub-methods
 *
 * @param nextTokenIndex The index of the next token to be parsed
 * @param parsedType A {@link JavaCodeType} that was parsed by the method
 *
 * @author Logan Ferree (Tadukoo)
 * @version Beta v.0.5
 */
public record ParsingPojo(int nextTokenIndex, JavaCodeType parsedType){
}
