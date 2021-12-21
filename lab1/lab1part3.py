import base64

file = base64.b64decode("G0IFOFVMLRAPI1QJbEQDbFEYOFEPJxAfI10JbEMFIUAAKRAfOVIfOFkYOUQFI15ML1kcJFUeYhA4IxAeKVQZL1VMOFgJbFMDIUAAKUgFOElMI1ZMOFgFPxADIlVMO1VMO1kAIBAZP1VMI14ANRAZPEAJPlMNP1VMIFUYOFUePxxMP19MOFgJbFsJNUMcLVMJbFkfbF8CIElMfgZNbGQDbFcJOBAYJFkfbF8CKRAeJVcEOBANOUQDIVEYJVMNIFwVbEkDORAbJVwAbEAeI1INLlwVbF4JKVRMOF9MOUMJbEMDIVVMP18eOBADKhALKV4JOFkPbFEAK18eJUQEIRBEO1gFL1hMO18eJ1UIbEQEKRAOKUMYbFwNP0RMNVUNPhlAbEMFIUUALUQJKBANIl4JLVwFIldMI0JMK0INKFkJIkRMKFUfL1UCOB5MH1UeJV8ZP1wVYBAbPlkYKRAFOBAeJVcEOBACI0dAbEkDORAbJVwAbF4JKVRMJURMOF9MKFUPJUAEKUJMOFgJbF4JNERMI14JbFEfbEcJIFxCbHIJLUJMJV5MIVkCKBxMOFgJPlWOzKkfbF4DbEMcLVMJPx5MRlgYOEAfdh9DKF8PPx4LI18LIFVCL18BY1QDL0UBKV4YY1RDfXg1e3QAYQUFOGkof3MzK1sZKXIaOnIqPGRYD1UPC2AFHgNcDkMtHlw4PGFDKVQFOA8ZP0BRP1gNPlkCKw==")
print(file)
print()

# підібране значення довжини ключа
KEYSIZE = 3

def hamming2(s1, s2):
    assert len(s1) == len(s2)
    return sum(c1 != c2 for c1, c2 in zip(s1, s2))


def distance(a, b):
    calc = 0
    for ca, cb in [(a[i], b[i]) for i in range(len(a))]:
        bina = '{:08b}'.format(int(ca))
        binb = '{:08b}'.format(int(cb))
        calc += hamming2(bina, binb)
    return calc

# print("distance: 'this is a test' and 'wokka wokka!!!' =", distance([ord(c) for c in "this is a test"], [ord(c) for c in "wokka wokka!!!"]))  # має бути 37
# print()


key_sizes = []
for KEYSIZE in range(2, 10):
    running_sum = []
    for i in range(0, int(len(file) / KEYSIZE) - 1):
        running_sum.append(distance(file[i * KEYSIZE:(i + 1) * KEYSIZE],
                                     file[(i + 1) * KEYSIZE:(i + 2) * KEYSIZE]) / KEYSIZE)
    key_sizes.append((sum(running_sum)/ len(running_sum), KEYSIZE))
key_sizes.sort(key=lambda a: a[0])

for val, key in key_sizes:
    print(key, ":", val)
KEYSIZE = key_sizes[0][1]
print()

splited_file = [[] for i in range(KEYSIZE)]
counter = 0
for char in file:
    splited_file[counter].append(char)
    counter += 1
    counter %= KEYSIZE
for line in splited_file:
    print(line)
print()


def single_char_string(a, b):
    final = ""
    for c in a:
        final += chr(c ^ b)
    return final


def find_single_byte(in_string):
    helper_list = []
    for num in range(256):
        helper_list.append((single_char_string(in_string, num), num))
    helper_list.sort(key=lambda a: a[0].count(' '), reverse=True)
    return helper_list[0]


final_key = "key: "
key_list = []
for line in splited_file:
    result = find_single_byte(line)
    print(result)
    final_key += chr(result[1])
    key_list.append(result[1])
print(final_key)
print(key_list)