package com.jaino.data.model

import com.jaino.domain.model.Employment
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
@XmlSerialName("response", "", "")
data class EmploymentAllResponse(
    val body: EmploymentBodyResponse,
    val header: EmploymentHeaderResponse
)

@Serializable
@XmlSerialName("body", "", "")
data class EmploymentBodyResponse(
    val items: EmploymentListResponse,
    @XmlElement(true) val numOfRows: Int,
    @XmlElement(true) val pageNo: Int,
    @XmlElement(true) val totalCount: Int
)

@Serializable
@XmlSerialName("header", "", "")
data class EmploymentHeaderResponse(
    @XmlElement(true) val resultCode: String,
    @XmlElement(true) val resultMsg: String
)

@Serializable
@XmlSerialName("items", "", "")
data class EmploymentListResponse(
    val item: List<EmploymentResponse>,
)

@Serializable
@XmlSerialName("item", "", "")
data class EmploymentResponse(
    @XmlElement(true) val bokrihs: String = "",
    @XmlElement(true) val ccdatabalsaengDtm: Int = 0,
    @XmlElement(true) val cjdatabyeongyeongDtm: Int = 0,
    @XmlElement(true) val cjhakryeok: String = "",
    @XmlElement(true) val cygonggoNo: Long = 0L,
    @XmlElement(true) val cyjemokNm: String = "",
    @XmlElement(true) val damdangjaFnm: String = "",
    @XmlElement(true) val ddeopmuNm: String = "",
    @XmlElement(true) val ddjyeonrakcheoNo: String = "",
    @XmlElement(true) val dpyeonrakcheoNo: String = "",
    @XmlElement(true) val eopcheNm: String = "",
    @XmlElement(true) val eopjongGbcd: Int = 0,
    @XmlElement(true) val eopjongGbcdNm: String = "",
    @XmlElement(true) val geunmujy: String = "",
    @XmlElement(true) val geunmujysido: String = "",
    @XmlElement(true) val gmhyeongtaeNm: String = "",
    @XmlElement(true) val gmjybjusoCd: Long = 0L,
    @XmlElement(true) val gyeongryeokGbcdNm: String = "",
    @XmlElement(true) val gyjogeonCd: Int = 0,
    @XmlElement(true) val gyjogeonCdNm: String = "",
    @XmlElement(true) val grNs: String = "",
    @XmlElement(true) val jggyeyeolCdNm: String = "",
    @XmlElement(true) val hmpgAddr: String = "",
    @XmlElement(true) val jeopsubb: String = "",
    @XmlElement(true) val jusoCd: Long = 0L,
    @XmlElement(true) val magamDt: Int = 0,
    @XmlElement(true) val mjinwonNm: String = "",
    @XmlElement(true) val saeopjaDrno: Long = 0L,
    @XmlElement(true) val yeokjongBrcd: Int = 0,
    @XmlElement(true) val yeokjongBrcdNm: String = "",
    @XmlElement(true) val yowonGbcd: Int = 0,
    @XmlElement(true) val yowonGbcdNm: String = "",
    @XmlElement(true) val yuhyoYn: String = ""
)

fun EmploymentResponse.toDomain() = Employment(
    number = cygonggoNo,
    title = cyjemokNm,
    company = eopcheNm,
    deadline = formatStringDate(magamDt),
    workTime = gmhyeongtaeNm,
    location = geunmujy,
    agentKind = "$yeokjongBrcdNm | $yowonGbcdNm",
    education = "$cjhakryeok | $jggyeyeolCdNm",
    career = grNs + gyeongryeokGbcdNm,
    salary = gyjogeonCdNm,
    work = ddeopmuNm,
    recruitNumber = mjinwonNm
)

private fun formatStringDate(date: Int): String {
    val stringDate = date.toString()
    val localDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyyMMdd"))
    return localDate.toString()
}