package services

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

class PasswordEncoderTest {
    @org.junit.jupiter.api.Test
    @Test
    fun testEncodePassword() {
        // voorbeeldwachtwoord opgeven
        val password = "examplePassword"
        val passwordEncoder = PasswordEncoder()
        val encodedPassword = passwordEncoder.encodePassword(password)
        val expectedHash = calculateExpectedHash(password)
        // Vergelijk de verwachte waarde met de daadwerkelijk berekende waarde
        assertEquals(expectedHash, encodedPassword)
    }

    // verwachte hash-waarde berekenen met behulp van SHA-256
    private fun calculateExpectedHash(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val encodedhash = digest.digest(password.toByteArray(StandardCharsets.UTF_8))
        return bytesToHex(encodedhash)
    }

    // array van bytes naar een hexadecimale string omzetten
    private fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuilder(2 * hash.size)
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }
}