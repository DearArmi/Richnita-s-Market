package com.example.mailbox

class API {

    private val userList = mutableListOf<User>()
    private val mailBoxList = mutableListOf<MailBox>()
    private var id = 0


    fun connectToURL(){

        userList.add(User("test", "test"))
        userList.add(User("DearArmi", "21327174a"))
        userList.add(User( "Fernan1994", "123456f"))
        userList.add(User( "GuilleWolf", "123456g"))

        mailBoxList.add(MailBox(1, -33.925859, 151.237818, MailBox.MailBoxStatus.UNLOCKED))
        mailBoxList.add(MailBox(2, -33.967154, 151.264715, MailBox.MailBoxStatus.LOCKED))
        mailBoxList.add(MailBox(3, -33.9438208, 151.2436039, MailBox.MailBoxStatus.UNLOCKED))
        mailBoxList.add(MailBox(4, -33.936577, 151.259410, MailBox.MailBoxStatus.LOCKED))


    }

    fun searchUser(user1:User):Boolean{

        var answer = false

        while (id < userList.size) {

            val user = userList[id]

            if (user1 == user) {

                answer = true
                break
            }
            id++
        }

        return answer
    }


    fun searchMailBox():MailBox{

        return mailBoxList[id]
    }

}