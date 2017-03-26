package com.redbubble.finchtemplate.api.v1.people

import com.redbubble.finchtemplate.model.Person
import com.redbubble.util.json.JsonPrinter._
import com.redbubble.util.spec.SpecHelper
import io.circe.syntax._
import org.scalacheck.Prop._
import org.scalacheck.Properties
import org.specs2.mutable.Specification

final class PeopleEncodersSpec extends Specification with SpecHelper {

  private implicit val personEncoder = PeopleEncoders.personEncoder

  val encodePersonProp = new Properties("Person encoding") {
    property("encode") = forAll { (p: Person) =>
      val expected = parse(s"""{"person":{"name":"${p.name}","birth_year":"${p.birthYear}","hair_colour":"${p.hairColour}"}}""")
      val actual = parse(jsonToString(p.asJson))
      expected must beEqualTo(actual)
    }
  }

  s2"Person can be encoded into JSON$encodePersonProp"

  val personResponseProp = new Properties("Person JSON response encoding") {
    property("encode") = forAll { (p: Person) =>
      val expected = parse(s"""{"data":{"person":{"name":"${p.name}","birth_year":"${p.birthYear}","hair_colour":"${p.hairColour}"}}}""")
      val actual = parse(jsonToString(p.asJson))
      expected must beEqualTo(actual)
    }
  }

  s2"Person can be encoded into a response JSON$personResponseProp"

  private implicit val peopleEncoder = PeopleEncoders.peopleEncoder

  val encodePeopleProp = new Properties("People encoding") {
    property("encode") = forAll { (p: Person) =>
      val expected = parse(s"""{"people":[{"name":"${p.name}","birth_year":"${p.birthYear}","hair_colour":"${p.hairColour}"}]}""")
      val actual = parse(jsonToString(Seq(p).asJson))
      expected must beEqualTo(actual)
    }
  }

  s2"People can be encoded into JSON$encodePeopleProp"

  val peopleResponseProp = new Properties("People JSON response encoding") {
    property("encode") = forAll { (p: Person) =>
      val expected = parse(s"""{"data":{"people":[{"name":"${p.name}","birth_year":"${p.birthYear}","hair_colour":"${p.hairColour}"}]}}""")
      val actual = parse(jsonToString(Seq(p).asJson))
      expected must beEqualTo(actual)
    }
  }

  s2"People can be encoded into a response JSON$peopleResponseProp"
}