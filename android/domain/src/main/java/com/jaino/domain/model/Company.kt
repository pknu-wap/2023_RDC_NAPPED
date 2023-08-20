package com.jaino.domain.model

/**
 * @property company 회사이름
 * @property timeLine 등록년도
 * @property kind 업종
 * @property location 근무위치
 * @property scale 기업규모
 */
data class Company(
    val company: String,
    val timeLine: String,
    val kind: String,
    val location: String,
    val scale: String
)