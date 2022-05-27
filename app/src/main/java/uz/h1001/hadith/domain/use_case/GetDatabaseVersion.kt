package uz.h1001.hadith.domain.use_case

import uz.h1001.hadith.domain.repositoy.HadithRepository

class GetDatabaseVersion(private val repository: HadithRepository) {
    operator fun invoke(): Long = repository.getDatabaseVersionFromRemoteConfig()
}