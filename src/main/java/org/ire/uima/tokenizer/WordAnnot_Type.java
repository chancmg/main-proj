package org.ire.uima.tokenizer;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.tcas.Annotation_Type;

/**
 * Updated by JCasGen Mon Nov 29 15:02:38 EST 2004
 * 
 * @generated
 */
public class WordAnnot_Type extends Annotation_Type {
	/** @generated */
	protected FSGenerator getFSGenerator() {
		return fsGenerator;
	}

	/** @generated */
	private final FSGenerator fsGenerator = new FSGenerator() {
		public FeatureStructure createFS(int addr, CASImpl cas) {
			if (instanceOf_Type.useExistingInstance) {
				// Return eq fs instance if already created
				FeatureStructure fs = instanceOf_Type.jcas
						.getJfsFromCaddr(addr);
				if (null == fs) {
					fs = new WordAnnot(addr, instanceOf_Type);
					instanceOf_Type.jcas.putJfsFromCaddr(addr, fs);
					return fs;
				}
				return fs;
			} else
				return new WordAnnot(addr, instanceOf_Type);
		}
	};

	/** @generated */
	public final static int typeIndexID = WordAnnot.typeIndexID;

	/**
	 * @generated
	 * @modifiable
	 */
	public final static boolean featOkTst = JCasRegistry
			.getFeatOkTst("org.ire.uima.tokenizer.WordAnnot");

	/**
	 * initialize variables to correspond with Cas Type and Features
	 * 
	 * @generated
	 */
	public WordAnnot_Type(JCas jcas, Type casType) {
		super(jcas, casType);
		casImpl.getFSClassRegistry().addGeneratorForType(
				(TypeImpl) this.casType, getFSGenerator());

	}
}
