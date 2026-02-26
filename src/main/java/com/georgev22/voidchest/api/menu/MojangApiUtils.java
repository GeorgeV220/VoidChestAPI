package com.georgev22.voidchest.api.menu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.UUID;

@Deprecated
public class MojangApiUtils {

    private static final String SKIN_DATA_URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=%s";
    private static final String NAME_AT_TIME_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%s";

    public static String @Nullable [] getSkinData(UUID uuid, boolean requestSignature) throws MojangApiException, IOException {
        String url = String.format(SKIN_DATA_URL, uuid, !requestSignature);
        Reader reader = new InputStreamReader(new URL(url).openConnection().getInputStream());
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

        checkForError(jsonObject);
        if (jsonObject.has("properties")) {
            JsonElement properties = jsonObject.get("properties");
            JsonObject property = properties.getAsJsonArray().get(0).getAsJsonObject();
            String value = property.get("value").getAsString();
            String signature = requestSignature ? property.get("signature").getAsString() : "";
            return new String[]{value, signature};
        }

        return null;
    }

    public static UUID getCurrentUuid(String name) throws MojangApiException, IOException {
        return getUuidAtTime(name, System.currentTimeMillis() / 1000);
    }

    public static @Nullable UUID getUuidAtTime(String name, long timestamp) throws MojangApiException, IOException {
        String url = String.format(NAME_AT_TIME_URL, name, timestamp);
        Reader reader = new InputStreamReader(new URL(url).openConnection().getInputStream());
        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();

        checkForError(jsonObject);
        if (jsonObject.has("id")) {
            String id = jsonObject.get("id").getAsString();
            return fromUndashed(id);
        }

        return null;
    }

    private static @NonNull UUID fromUndashed(@NonNull String undashed) {
        return UUID.fromString(undashed.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

    private static void checkForError(@NonNull JsonObject jsonObject) throws MojangApiException {
        if (jsonObject.has("error"))
            throw new MojangApiException(jsonObject);
    }

    public static class MojangApiException extends Exception {

        private final JsonObject response;

        public MojangApiException(@NonNull JsonObject response) {
            super(response.has("errorMessage") ? response.get("errorMessage").getAsString() : "");
            this.response = response;
        }

        public JsonObject getResponse() {
            return response;
        }

    }

}