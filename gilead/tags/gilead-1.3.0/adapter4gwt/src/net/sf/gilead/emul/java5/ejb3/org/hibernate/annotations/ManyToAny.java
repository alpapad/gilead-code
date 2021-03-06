//$Id$
package org.hibernate.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import javax.persistence.Column;
import javax.persistence.FetchType;
import static javax.persistence.FetchType.EAGER;

/**
 * Defined a ToMany association pointing to different entity types.
 * Matching the according entity type is doe through a metadata discriminator column
 * This kind of mapping should be only marginal.
 *
 * @author Emmanuel Bernard
 */
@java.lang.annotation.Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ManyToAny {
	/**
	 * Metadata definition used.
	 * If defined, should point to a @AnyMetaDef name
	 * If not defined, the local (ie in the same field or property) @AnyMetaDef is used
	 */
	String metaDef() default "";

	/**
	 * Metadata dicriminator column description, This column will hold the meta value corresponding to the
	 * targeted entity.
	 */
	Column metaColumn();
	/**
	 * Defines whether the value of the field or property should be lazily loaded or must be
	 * eagerly fetched. The EAGER strategy is a requirement on the persistence provider runtime
	 * that the value must be eagerly fetched. The LAZY strategy is applied when bytecode
	 * enhancement is used. If not specified, defaults to EAGER.
	 */
	FetchType fetch() default EAGER;
}
