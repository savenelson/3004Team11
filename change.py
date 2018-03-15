def changeph(string):

    WORDS = "BY THE WAY"
    checking = WORDS.split()
    wordlist = string.split()
    pos = 0
    index1 = -1
    index2 = -1
    index3 = -1

    output = ""

    for i in range(len(wordlist)-2):
        if wordlist[i] == checking[0] and wordlist[i+1] == checking[1] and wordlist[i+2] == checking[2]:
            index1 = i
            index2 = i+1
            index3 = i+2

            break

    for j in range(len(wordlist)):
        if j== index1:
            output+= "BTW"

            j=j+2
            

        else:
            output+= wordlist[j]
            output+= " "


    print(output)



changeph("Dave BY THE WAY")
