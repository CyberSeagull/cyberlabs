import ratings
import random

TRYES = 50
STAIRS_PER_TRYES = 3000
rating_dictionary = ratings.trigrams

def convert(ciphertext, testkey):
    for i in range(len(testkey)):
        plain = chr(i+65).lower()
        ciphertext = ciphertext.replace(testkey[i], plain)
    return ciphertext.upper()

def rate(text):
    rating = 0
    for window_right_index in range(3, len(text)):
        trigramStr = text[window_right_index - 3:window_right_index]
        if trigramStr in rating_dictionary:
            rating += rating_dictionary[trigramStr]
    return rating

def main():
    ciphertext = "EFFPQLEKVTVPCPYFLMVHQLUEWCNVWFYGHYTCETHQEKLPVMSAKSPVPAPVYWMVHQLUSPQLYWLASLFVWPQLMVHQLUPLRPSQLULQESPBLWPCSVRVWFLHLWFLWPUEWFYOTCMQYSLWOYWYETHQEKLPVMSAKSPVPAPVYWHEPPLUWSGYULEMQTLPPLUGUYOLWDTVSQETHQEKLPVPVSMTLEUPQEPCYAMEWWYTYWDLUULTCYWPQLSEOLSVOHTLUYAPVWLYGDALSSVWDPQLNLCKCLRQEASPVILSLEUMQBQVMQCYAHUYKEKTCASLFPYFLMVHQLUPQLHULIVYASHEUEDUEHQBVTTPQLVWFLRYGMYVWMVFLWMLSPVTTBYUNESESADDLSPVYWCYAMEWPUCPYFVIVFLPQLOLSSEDLVWHEUPSKCPQLWAOKLUYGMQEUEMPLUSVWENLCEWFEHHTCGULXALWMCEWETCSVSPYLEMQYGPQLOMEWCYAGVWFEBECPYASLQVDQLUYUFLUGULXALWMCSPEPVSPVMSBVPQPQVSPCHLYGMVHQLUPQLWLRPOEDVMETBYUFBVTTPENLPYPQLWLRPTEKLWZYCKVPTCSTESQPBYMEHVPETCMEHVPETZMEHVPETKTMEHVPETCMEHVPETT"
    plain_bank = list('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
    crypt_bank = list('ABCDEFGHIJKLMNOPQRSTUVWXYZ')
    best_key = ""
    best_rating = 0
    for key_num in range(1, TRYES):
        plain_to_cipher_dict = {}
        crypt_bank_copy = crypt_bank.copy()
        for plain in plain_bank:
            rand = 0
            if len(crypt_bank_copy) > 0:
                rand = random.randint(0, len(crypt_bank_copy) - 1)
            plain_to_cipher_dict[plain] = crypt_bank_copy[rand]
            del crypt_bank_copy[rand]
        key = list((([v for (k, v) in sorted(plain_to_cipher_dict.items())])))
        hill_rating = rate(convert(ciphertext, key))
        j = 0
        while j < STAIRS_PER_TRYES:
            rand1 = random.randint(0, 25)
            rand2 = random.randint(0, 25)
            while rand2 == rand1:
                rand2 = random.randint(0, 25)
            new_key = list(key[:])
            new_key[rand1], new_key[rand2] = key[rand2], key[rand1]
            new_rating = rate(convert(ciphertext, new_key))
            if new_rating > hill_rating:
                hill_rating = new_rating
                key = new_key
                j = 0
            j += 1
        if hill_rating > best_rating:
            best_key = key
            best_rating = hill_rating
        print(F"Key {key_num:2}: {key} with rating: {hill_rating}")
    print(F"Best key found: {best_key}, rating: {best_rating}\nApplied to input:\n{convert(ciphertext,best_key)}")

if __name__ == "__main__":
    main()
