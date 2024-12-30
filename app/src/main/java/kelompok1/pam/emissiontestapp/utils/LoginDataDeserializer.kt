package kelompok1.pam.emissiontestapp.utils

import com.google.gson.*
import kelompok1.pam.emissiontestapp.data.model.LoginData
import java.lang.reflect.Type

class LoginDataDeserializer : JsonDeserializer<LoginData?> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LoginData? {
        return if (json.isJsonObject) {
            context.deserialize(json, LoginData::class.java)
        } else {
            // Return null for empty array `[]`
            null
        }
    }
}
