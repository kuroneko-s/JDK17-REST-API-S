package com.example.learing.jdk17;

// CarBrand는 상속, 구현은 Hyndai, Kia만 가능하다. [가능 타입을 permits로 나열]
sealed interface CarBrand permits Hyundai, Kia{}
// 상속 불가 클래스 (다 아는거)
final class Hyundai implements CarBrand {}
// 제한없이 상속, 구현 가능. [기본옵션]
non-sealed class Kia implements CarBrand {}

// Error
// class Toyota implements CarBrand {}

public class SealedClassTest {
}
