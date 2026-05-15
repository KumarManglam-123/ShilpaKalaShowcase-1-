package com.shilpakala.showcase.data.repository

import com.shilpakala.showcase.core.resource.Resource
import com.shilpakala.showcase.core.resource.safeCall
import com.shilpakala.showcase.core.utils.DateTimeUtils
import com.shilpakala.showcase.data.local.db.dao.HeritageDao
import com.shilpakala.showcase.data.mapper.toDomain
import com.shilpakala.showcase.data.mapper.toEntity
import com.shilpakala.showcase.domain.model.Heritage
import com.shilpakala.showcase.domain.repository.HeritageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Heritage repository that uses a local fallback source when API is unavailable.
 * Implements offline-first by caching all narratives in Room.
 */
@Singleton
class HeritageRepositoryImpl @Inject constructor(
    private val heritageDao: HeritageDao
) : HeritageRepository {

    override fun observeAllHeritage(): Flow<List<Heritage>> {
        return heritageDao.observeAllHeritage().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeHeritageByLanguage(language: String): Flow<List<Heritage>> {
        return heritageDao.observeHeritageByLanguage(language).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getHeritageById(id: String): Resource<Heritage> = safeCall {
        val entity = heritageDao.getHeritageById(id)
            ?: throw IllegalArgumentException("Heritage not found: $id")
        entity.toDomain()
    }

    override suspend fun generateNarrative(
        style: String,
        language: String
    ): Resource<Heritage> = safeCall {
        // Check cache first
        val cached = heritageDao.getHeritageByStyle(style, language)
        if (cached != null) {
            return@safeCall cached.toDomain()
        }

        // Use local fallback content (production would call Gemini API here)
        val narrative = getLocalFallbackNarrative(style, language)
        val heritage = Heritage(
            id = UUID.randomUUID().toString(),
            title = getHeritageTitle(style, language),
            style = style,
            narrative = narrative,
            imageUrl = null,
            isAiGenerated = false,
            language = language,
            cachedAt = DateTimeUtils.currentTimeMillis()
        )

        heritageDao.insertHeritage(heritage.toEntity())
        heritage
    }

    override suspend fun refreshHeritage(language: String): Resource<List<Heritage>> = safeCall {
        val count = heritageDao.getCount(language)
        if (count == 0) {
            // Seed with local fallback data
            val heritageList = getDefaultHeritageList(language)
            heritageDao.insertHeritageList(heritageList.map { it.toEntity() })
        }
        heritageDao.observeHeritageByLanguage(language).map { entities ->
            entities.map { it.toDomain() }
        }
        // Return current cached data
        val entities = heritageDao.observeHeritageByLanguage(language)
        getDefaultHeritageList(language)
    }

    override suspend fun clearCache() {
        heritageDao.deleteAll()
    }

    private fun getDefaultHeritageList(language: String): List<Heritage> {
        val now = DateTimeUtils.currentTimeMillis()
        return listOf(
            Heritage(
                id = "heritage_hoysala_$language",
                title = getHeritageTitle("Hoysala", language),
                style = "Hoysala",
                narrative = getLocalFallbackNarrative("Hoysala", language),
                imageUrl = null,
                isAiGenerated = false,
                language = language,
                cachedAt = now
            ),
            Heritage(
                id = "heritage_dravidian_$language",
                title = getHeritageTitle("Dravidian", language),
                style = "Dravidian",
                narrative = getLocalFallbackNarrative("Dravidian", language),
                imageUrl = null,
                isAiGenerated = false,
                language = language,
                cachedAt = now
            ),
            Heritage(
                id = "heritage_vijayanagara_$language",
                title = getHeritageTitle("Vijayanagara", language),
                style = "Vijayanagara",
                narrative = getLocalFallbackNarrative("Vijayanagara", language),
                imageUrl = null,
                isAiGenerated = false,
                language = language,
                cachedAt = now
            ),
            Heritage(
                id = "heritage_folk_$language",
                title = getHeritageTitle("Folk", language),
                style = "Folk",
                narrative = getLocalFallbackNarrative("Folk", language),
                imageUrl = null,
                isAiGenerated = false,
                language = language,
                cachedAt = now
            ),
            Heritage(
                id = "heritage_channapatna_$language",
                title = getHeritageTitle("Channapatna", language),
                style = "Channapatna",
                narrative = getLocalFallbackNarrative("Channapatna", language),
                imageUrl = null,
                isAiGenerated = false,
                language = language,
                cachedAt = now
            )
        )
    }

    private fun getHeritageTitle(style: String, language: String): String {
        if (language == "kn") {
            return when (style) {
                "Hoysala" -> "ಹೊಯ್ಸಳ ಶಿಲ್ಪಕಲೆ"
                "Dravidian" -> "ದ್ರಾವಿಡ ವಾಸ್ತುಶಿಲ್ಪ"
                "Vijayanagara" -> "ವಿಜಯನಗರ ಶಿಲ್ಪಕಲೆ"
                "Folk" -> "ಜಾನಪದ ಕಲೆ"
                "Channapatna" -> "ಚನ್ನಪಟ್ಟಣ ಆಟಿಕೆಗಳು"
                else -> style
            }
        }
        return when (style) {
            "Hoysala" -> "Hoysala Temple Sculpture"
            "Dravidian" -> "Dravidian Architecture"
            "Vijayanagara" -> "Vijayanagara Sculpture"
            "Folk" -> "Folk Art Traditions"
            "Channapatna" -> "Channapatna Craft"
            else -> style
        }
    }

    private fun getLocalFallbackNarrative(style: String, language: String): String {
        if (language == "kn") {
            return when (style) {
                "Hoysala" -> "ಹೊಯ್ಸಳ ಸಾಮ್ರಾಜ್ಯವು 10-14ನೇ ಶತಮಾನದಲ್ಲಿ ಕರ್ನಾಟಕವನ್ನು ಆಳಿತು. ಅವರ ದೇವಾಲಯಗಳು ಅತ್ಯಂತ ಸೂಕ್ಷ್ಮ ಕೆತ್ತನೆಗಳಿಗೆ ಪ್ರಸಿದ್ಧವಾಗಿವೆ. ಬೇಲೂರು, ಹಳೇಬೀಡು ಮತ್ತು ಸೋಮನಾಥಪುರದ ದೇವಾಲಯಗಳು ಜಾಗತಿಕ ಪರಂಪರೆಯ ತಾಣಗಳಾಗಿವೆ. ಮೃದು ಕಲ್ಲಿನ ಮೇಲೆ ನಕ್ಷತ್ರಾಕಾರದ ಯೋಜನೆಯಲ್ಲಿ ಮಾಡಿದ ಕೆತ್ತನೆಗಳು ಅದ್ಭುತವಾಗಿವೆ."
                "Dravidian" -> "ದ್ರಾವಿಡ ವಾಸ್ತುಶಿಲ್ಪವು ದಕ್ಷಿಣ ಭಾರತದ ಅತ್ಯಂತ ಪ್ರಾಚೀನ ಮತ್ತು ಪ್ರಭಾವಶಾಲಿ ಶೈಲಿಯಾಗಿದೆ. ಎತ್ತರದ ಗೋಪುರಗಳು, ಮಂಟಪಗಳು, ಮತ್ತು ವಿಸ್ತಾರವಾದ ಪ್ರಾಕಾರಗಳು ಇದರ ವಿಶೇಷತೆಗಳಾಗಿವೆ."
                "Vijayanagara" -> "ವಿಜಯನಗರ ಸಾಮ್ರಾಜ್ಯವು 14-17ನೇ ಶತಮಾನದಲ್ಲಿ ದಕ್ಷಿಣ ಭಾರತವನ್ನು ಆಳಿತು. ಹಂಪಿಯ ಅವಶೇಷಗಳು UNESCO ವಿಶ್ವ ಪರಂಪರೆಯ ತಾಣವಾಗಿವೆ. ಕಲ್ಲಿನ ರಥಗಳು, ಸಂಗೀತ ಕಂಬಗಳು ಮತ್ತು ಬೃಹತ್ ಏಕಶಿಲಾ ಶಿಲ್ಪಗಳು ಪ್ರಸಿದ್ಧವಾಗಿವೆ."
                "Folk" -> "ಕರ್ನಾಟಕದ ಜಾನಪದ ಕಲೆಯು ಗ್ರಾಮೀಣ ಜೀವನದ ಸಾರವನ್ನು ಪ್ರತಿಬಿಂಬಿಸುತ್ತದೆ. ಮರದ ಆಟಿಕೆಗಳು, ಕಂಚಿನ ಮೂರ್ತಿಗಳು, ಮತ್ತು ಕಲ್ಲಿನ ವೀರಗಲ್ಲುಗಳು ಈ ಪರಂಪರೆಯ ಭಾಗವಾಗಿವೆ."
                "Channapatna" -> "ಚನ್ನಪಟ್ಟಣವು ಕರ್ನಾಟಕದ ರಾಮನಗರ ಜಿಲ್ಲೆಯಲ್ಲಿರುವ ಆಟಿಕೆ ಪಟ್ಟಣ. ಹಲೇ ಮರದಿಂದ ತಯಾರಿಸಿದ ಬಣ್ಣಬಣ್ಣದ ಆಟಿಕೆಗಳಿಗೆ GI ಟ್ಯಾಗ್ ದೊರೆತಿದೆ. ಟಿಪ್ಪು ಸುಲ್ತಾನನ ಕಾಲದಿಂದ ಈ ಕಲೆ ಪ್ರಸಿದ್ಧವಾಗಿದೆ."
                else -> ""
            }
        }
        return when (style) {
            "Hoysala" -> "The Hoysala Empire ruled Karnataka from the 10th to 14th century, leaving behind a legacy of extraordinary temple sculpture. The temples at Belur, Halebidu, and Somnathpur are UNESCO World Heritage Sites renowned for their intricate star-shaped plans and impossibly detailed stone carvings. Hoysala artisans worked in soft soapstone (chloritic schist), which allowed them to achieve a level of detail that rivals ivory carving. Each temple surface is covered with a profusion of Hindu mythological scenes, scrollwork, and naturalistic animal and plant motifs. The lathe-turned pillars and bracket figures (madanika) represent the pinnacle of Indian sculptural art. These master craftsmen developed unique techniques including undercutting and filigree-like stone lattice work that remain unmatched to this day."
            "Dravidian" -> "Dravidian architecture represents one of the most ancient and influential architectural traditions of South India, spanning over two millennia. Characterized by towering gopurams (gateway towers), pillared mandapas (halls), and vast temple complexes surrounded by concentric prakaras (walls), this style reached its zenith during the Pallava, Chola, and Pandya dynasties. The rock-cut temples of Mahabalipuram and the monumental Brihadeeswarar Temple in Thanjavur exemplify the engineering brilliance and artistic mastery of Dravidian architects. Every surface is adorned with elaborate sculptures depicting celestial beings, mythological narratives, and geometric patterns that demonstrate the sophisticated understanding of proportion and symmetry that defined this tradition."
            "Vijayanagara" -> "The Vijayanagara Empire (14th-17th century) created one of the most magnificent architectural legacies in the Indian subcontinent. The ruins at Hampi, a UNESCO World Heritage Site, showcase a unique blend of Dravidian, Islamic, and Jain architectural elements. The empire's artisans perfected the art of monolithic sculpture, creating massive stone chariots, musical pillars that produce different notes when struck, and elaborate bas-reliefs depicting scenes from the Ramayana and Mahabharata. The Vittala Temple complex, with its iconic stone chariot and 56 musical pillars, represents the artistic zenith of this period. Vijayanagara sculptors worked primarily in granite, developing innovative techniques for carving this harder stone with remarkable precision and grace."
            "Folk" -> "Karnataka's folk art traditions represent an unbroken chain of creative expression stretching back centuries, deeply rooted in rural life and community ritual. From the wooden Gombe (dolls) of Channapatna to the bronze castings of Bidriware artisans, these traditions embody the aesthetic sensibilities and spiritual beliefs of diverse communities. Hero stones (Virgal) carved to commemorate warriors, intricately carved wooden door frames of rural mansions, and painted terracotta horses offered at village shrines all form part of this rich tapestry. Unlike classical traditions, folk art prioritizes emotional resonance and community function over technical perfection, creating works of profound authenticity and vitality."
            "Channapatna" -> "Channapatna, a small town in Karnataka's Ramanagara district, has been the center of a vibrant lacquerware and wooden toy tradition for over 200 years. Legend attributes its origins to Tipu Sultan, who invited Persian artisans to train local craftsmen. Today, Channapatna toys bear a Geographical Indication (GI) tag, recognizing their unique cultural heritage. Artisans use Hale wood (Wrightia tinctoria), which is soft, lightweight, and easily turned on hand-operated lathes. The toys are coated with natural dyes extracted from seeds, lac, and plant sources, making them safe for children. From spinning tops and kitchen sets to elaborate chess pieces and decorative items, Channapatna craftsmen create over 500 different designs that blend traditional forms with contemporary appeal."
            else -> ""
        }
    }
}
