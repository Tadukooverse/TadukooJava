package com.github.tadukoo.java.uneditable;

import com.github.tadukoo.java.Javadoc;
import com.github.tadukoo.java.JavadocBuilder;
import com.github.tadukoo.util.tuple.Pair;

import java.util.List;

/**
 * Represents a Javadoc that can't be modified
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 * @since Alpha v.0.3.3 (as Javadoc), Alpha v.0.4 (as UneditableJavadoc)
 */
public class UneditableJavadoc extends Javadoc{
	
	/**
	 * A builder used to make an {@link UneditableJavadoc}.
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 * @see JavadocBuilder
	 */
	public static class UneditableJavadocBuilder extends JavadocBuilder<UneditableJavadoc>{
		
		/** Not allowed to instantiate outside {@link UneditableJavadoc} */
		private UneditableJavadocBuilder(){
			super();
		}
		
		/** {@inheritDoc} */
		@Override
		protected UneditableJavadoc constructJavadoc(){
			return new UneditableJavadoc(condensed, content, author, version, since, params, returnVal);
		}
	}
	
	/**
	 * Constructs a new {@link UneditableJavadoc} using the given parameters
	 *
	 * @param condensed Whether the {@link Javadoc} is condensed or not
	 * @param content The content of the {@link Javadoc}
	 * @param author The author of the {@link Javadoc}
	 * @param version The version for the {@link Javadoc}
	 * @param since The "since" value for the {@link Javadoc}
	 * @param params The parameters in the {@link Javadoc}
	 * @param returnVal The return string in the {@link Javadoc}
	 */
	private UneditableJavadoc(
			boolean condensed, List<String> content, String author, String version, String since,
			List<Pair<String, String>> params, String returnVal){
		super(false, condensed, content, author, version, since, params, returnVal);
	}
	
	/**
	 * @return A new {@link UneditableJavadocBuilder} to use to build an {@link UneditableJavadoc}
	 */
	public static UneditableJavadocBuilder builder(){
		return new UneditableJavadocBuilder();
	}
}
