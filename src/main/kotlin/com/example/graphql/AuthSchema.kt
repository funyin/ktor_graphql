package com.example.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.User
import com.example.models.UserInput
import com.example.services.AuthService
import java.lang.Exception

fun SchemaBuilder.authSchema(authService: AuthService){

    mutation("signIn"){
        description = "Authenticate an existing user"

        resolver {userInput:UserInput->
            try {
                authService.signIn(userInput)
            }catch (e:Exception){
                null
            }
        }
    }

    type<User>{
        description = "A user object"
        User::hashedPass.ignore()
    }
    mutation("signUp"){
        description = "Authenticate an new user"

        resolver {userInput:UserInput->
            try {
                authService.signUp(userInput)
            }catch (e:Exception){
                null
            }
        }
    }
}