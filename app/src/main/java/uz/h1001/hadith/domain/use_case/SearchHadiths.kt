package uz.h1001.hadith.domain.use_case

import uz.h1001.hadith.domain.repositoy.HadithRepository

class SearchHadiths(
    private val repository: HadithRepository
) {
    operator fun invoke(query: String) = repository.searchHadithsFromFireStore(query = query)
}