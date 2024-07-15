package com.openclassrooms.vitesse

import com.openclassrooms.vitesse.data.dao.CandidatDtoDao
import com.openclassrooms.vitesse.data.entity.CandidatDto
import com.openclassrooms.vitesse.data.repository.CandidatsRepository
import com.openclassrooms.vitesse.domain.model.Candidat
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CandidatsRepositoryTest {

    @Mock
    private lateinit var candidatDao: CandidatDtoDao
    private lateinit var repository: CandidatsRepository

    @Before
    fun setup() {
        repository = CandidatsRepository(candidatDao)
    }

    @Test
    fun testGetAllCandidats() {
        runBlocking {
            val candidats = listOf(
                CandidatDto(
                    id = 1,
                    prenom = "Ben",
                    nom = "JOJO",
                    phone = "1234567890",
                    email = "ben@gmail.com",
                    dateBirth = "01-01-1990",
                    pretend = 5000.0,
                    note = "Note",
                    favori = false,
                    picture = "male29"
                ),
                CandidatDto(
                    id = 2,
                    prenom = "Josiane",
                    nom = "PIPO",
                    phone = "0987654321",
                    email = "josiane@gmail.com",
                    dateBirth = "02-02-1991",
                    pretend = 6000.0,
                    note = "Note",
                    favori = true,
                    picture = "female23"
                )
            )
            `when`(candidatDao.getAllCandidat()).thenReturn(flowOf(candidats))

            val result = repository.getAllCandidats()

            assertNotNull(result)
            result.collect { list ->
                assertEquals(2, list.size)
                assertEquals("Ben", list[0].prenom)
                assertEquals("PIPO", list[1].nom)
            }
            verify(candidatDao).getAllCandidat()
        }
    }

    @Test
    fun testDeleteCandidatById() {
        runBlocking {
            val candidatId = 1L
            repository.deleteCandidatById(candidatId)
            verify(candidatDao).deleteCandidatById(candidatId)
        }
    }
    @Test
    fun testUpdateCandidat() {
        runBlocking {
            val candidat = Candidat(
                id = 1,
                prenom = "Ben",
                nom = "JOJO",
                phone = "1234567890",
                email = "ben@gmail.com",
                dateBirth = "01-01-1990",
                pretend = 5000.0,
                note = "Note",
                favori = false,
                picture = "male29"
            )

            repository.updateCandidat(candidat)
            verify(candidatDao).updateCandidat(candidat.toDto())
        }
    }
    @Test
    fun testInsertCandidat() {
        runBlocking {
            val candidat = Candidat(
                id = 1,
                prenom = "Ben",
                nom = "JOJO",
                phone = "1234567890",
                email = "ben@gmail.com",
                dateBirth = "01-01-1990",
                pretend = 5000.0,
                note = "Note",
                favori = false,
                picture = "male29"
            )

            repository.insertCandidat(candidat)
            verify(candidatDao).insertCandidat(candidat.toDto())
        }
    }

    @Test
    fun testGetFavoris() {
        runBlocking {
            val favoris = listOf(
                CandidatDto(
                    id = 1,
                    prenom = "Ben",
                    nom = "JOJO",
                    phone = "1234567890",
                    email = "ben@gmail.com",
                    dateBirth = "01-01-1990",
                    pretend = 5000.0,
                    note = "Note",
                    favori = true,
                    picture = "male29"
                ),
                CandidatDto(
                    id = 2,
                    prenom = "Josiane",
                    nom = "PIPO",
                    phone = "0987654321",
                    email = "josiane@gmail.com",
                    dateBirth = "02-02-1991",
                    pretend = 6000.0,
                    note = "Note",
                    favori = true,
                    picture = "female23"
                )
            )
            `when`(candidatDao.getFavoris()).thenReturn(flowOf(favoris))

            val result = repository.getFavoris()

            assertNotNull(result)
            result.collect { list ->
                assertEquals(2, list.size)
                assertEquals("Ben", list[0].prenom)
                assertEquals("Josiane", list[1].prenom)
            }
            verify(candidatDao).getFavoris()
        }
    }
    @Test
    fun testGetCandidatById() {
        runBlocking {
            val candidatId = 1L
            val candidatDto = CandidatDto(
                id = 1,
                prenom = "Ben",
                nom = "JOJO",
                phone = "1234567890",
                email = "ben@gmail.com",
                dateBirth = "01-01-1990",
                pretend = 5000.0,
                note = "Note",
                favori = false,
                picture = "male29"
            )

            `when`(candidatDao.getCandidatById(candidatId)).thenReturn(candidatDto)

            val result = repository.getCandidatById(candidatId)

            assertNotNull(result)
            assertEquals(candidatDto, result)
            verify(candidatDao).getCandidatById(candidatId)
        }
    }
}

