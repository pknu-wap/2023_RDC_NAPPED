package com.jaino.domain.model

/**
 * @property number 채용번호
 * @property title 채용제목
 * @property company 회사이름
 * @property deadline 마감기한
 * @property workTime 근무시간
 * @property location 근무지역
 * @property kind 업종
 * @property agentKind 요원구분
 * @property education 학력
 * @property career 경력
 * @property salary 급여
 * @property work 담당업무
 * @property recruitNumber 채용인원
 *
 */

data class Employment(
    val number: Long,
    val title: String,
    val company: String,
    val deadline: String,
    val workTime: String,
    val location: String,
    val kind : String,
    val agentKind: String,
    val education: String,
    val career: String,
    val salary: String,
    val work: String,
    val recruitNumber: String
){
    constructor(): this(
        0L,
        "",
        "",
        "2024.01.01",
        "09:00 ~ 21:00",
        "",
        "",
        "산업기능요원",
        "고등학교 졸업",
        "신입",
        "2400",
        "",
        "3명"
    )
}