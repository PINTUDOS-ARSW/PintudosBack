package pintudos.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pintudos.game.security.AESCipher;

public class AESCipherTest {

    @Test
    public void testEncryptDecrypt() throws Exception {
        String original = "hola";
        
        String encrypted = AESCipher.encrypt(original);
        assertNotNull(encrypted);
        System.out.println("Encrypted: " + encrypted);
        
        String decrypted = AESCipher.decrypt(encrypted);
        assertEquals(original, decrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
