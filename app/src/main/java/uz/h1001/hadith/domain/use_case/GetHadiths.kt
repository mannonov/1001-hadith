package uz.h1001.hadith.domain.use_case

import uz.h1001.hadith.domain.repositoy.HadithRepository

class GetHadiths(
    private val repository: HadithRepository
) {
    suspend operator fun invoke() = repository.getHadithsFromFireStore()
}