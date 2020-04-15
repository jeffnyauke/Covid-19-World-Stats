package dev.jeffnyauke.covid19stats.model.enums

enum class Order(val tag: String, val desc: String) {
    COUNTRY_NAME("countries", "Alphabetical"),
    CASES("cases", "Cases"),
    TODAY_CASES("todayCases", "Cases today"),
    DEATHS("deaths", "Deaths"),
    TODAY_DEATHS("todayDeaths", "Deaths today"),
    RECOVERED("recovered", "Recoveries"),
    ACTIVE("active", "Active cases"),
    CRITICAL("critical", "Critical cases"),
    CASES_PER_ONE_MILLION("casesPerOneMillion", "Cases per one million"),
    DEATHS_PER_ONE_MILLION("deathsPerOneMillion", "Deaths per one million")
}