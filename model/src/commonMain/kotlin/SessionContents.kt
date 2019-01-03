package io.github.droidkaigi.confsched2019.model

data class SessionContents(
    val sessions: List<Session>,
    val speakers: List<Speaker>,
    val rooms: List<Room>,
    val langs: List<Lang>,
    val topics: List<Topic>
) {
    companion object {
        val EMPTY = SessionContents(
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf()
        )
    }

    fun search(query: String): SearchResult {
        return SearchResult(
            sessions.filter {
                when (it) {
                    is Session.SpeechSession ->
                        find(query, it.title, it.desc)
                    is Session.ServiceSession ->
                        find(query, it.title)
                }
            },
            speakers.filter {
                find(query, it.name, it.tagLine, it.githubUrl, it.twitterUrl)
            }
        )
    }

    private fun find(query: String, vararg strings: String?): Boolean {
        return strings
            .find { it?.contains(query, true) ?: false } != null
    }
}
