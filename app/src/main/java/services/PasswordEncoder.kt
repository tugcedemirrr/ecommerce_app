package services

import java.nio.charset.StandardCharsets
import java.security.MessageDigest


class PasswordEncoder {

    // Wachtwoord coderen met SHA-256 en de resultaten als hexadecimale string teruggeven.
    fun encodePassword(password: String): String {
        // instantie van MessageDigest wordt gemaakt met het SHA-256 algoritme
        // wachtwoord wordt omgezet in een array van bytes en vervolgens gehasht met SHA-256
        // gehashte waarde wordt omgezet in een hexadecimale string en gereturned
        val digest = MessageDigest.getInstance("SHA-256")
        val encodedhash = digest.digest(password.toByteArray(StandardCharsets.UTF_8))
        return bytesToHex(encodedhash)
    }

    // Array van bytes naar een hexadecimale string omzetten
    private fun bytesToHex(hash: ByteArray): String {
        val hexString = StringBuilder(2 * hash.size)
        // Loopt door array van bytes en zet elk byte om in zijn hexadecimale representatie
        for (i in hash.indices) {
            val hex = Integer.toHexString(0xff and hash[i].toInt())
            // Voegt een voorloopnul toe als hexadecimale representatie slechts één karakter heeft
            if (hex.length == 1) {
                hexString.append('0')
            }
            hexString.append(hex)
        }
        return hexString.toString()
    }
}