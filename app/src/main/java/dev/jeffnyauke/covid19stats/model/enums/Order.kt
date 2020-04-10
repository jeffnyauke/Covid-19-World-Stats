package dev.jeffnyauke.covid19stats.model.enums

enum class Order(val url: String, val desc: String) {
    COUNTRY_NAME("country", "Most Popular"),
    CASES("cases", "Most Popular"),
    TODAY_CASES("todayCases", "Highest Rated"),
    DEATHS("deaths", "Highest Rated"),
    TODAY_DEATHS("todayDeaths", "Highest Rated"),
    RECOVERED("recovered", "Highest Rated"),
    ACTIVE("active", "Highest Rated"),
    CRITICAL("critical", "Highest Rated"),
    CASES_PER_ONE_MILLION("casesPerOneMillion", "Highest Rated"),
    DEATHS_PER_ONE_MILLION("deathsPerOneMillion", "Highest Rated")
}