package argonaut

import scalaz._, Scalaz._
import org.scalacheck._, Arbitrary._, Prop._
import org.specs2._, org.specs2.specification._
import Argonaut._

object EncodeJsonSpecification extends Specification with ScalaCheck { def is = s2"""
  EncodeJson Witness Compilation
    Witness basics                        ${ok}
    Witness tuples                        ${ok}
    Witness auto                          ${ok}
    Witness derived                       ${ok}
  EncodeJson derive
    BackTicks                             ${derived.testBackTicksEncodeJson}
"""

  object primitives {
    EncodeJson.of[String]
    EncodeJson.of[Int]
    EncodeJson.of[Boolean]
    EncodeJson.of[Long]
    EncodeJson.of[Double]
    EncodeJson.of[Short]
    EncodeJson.of[Byte]
    EncodeJson.of[Option[Int]]
    EncodeJson.of[Option[String]]
  }

  object tuples {
    EncodeJson.of[(String, Int)]
    EncodeJson.of[(String, Int, Boolean)]
    EncodeJson.of[(String, Int, Boolean, Long)]
    EncodeJson.of[(String, Int, Boolean, Long, Double)]
  }

  object derived {
    import TestTypes._

    implicit def ProductEncodeJson: EncodeJson[Product] = EncodeJson.derive[Product]
    implicit def OrderLineEncodeJson: EncodeJson[OrderLine] = EncodeJson.derive[OrderLine]
    implicit def OrderEncodeJson: EncodeJson[Order] = EncodeJson.derive[Order]
    implicit def PersonEncodeJson: EncodeJson[Person] = EncodeJson.derive[Person]

    EncodeJson.of[Person]

    implicit def BackTicksEncodeJson: EncodeJson[BackTicks] = EncodeJson.derive[BackTicks]
    def testBackTicksEncodeJson = BackTicks("test").jencode === ("a.b.c" := "test") ->: jEmptyObject
  }
}
