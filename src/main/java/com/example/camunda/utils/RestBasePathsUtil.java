package com.example.camunda.utils;

public final class RestBasePathsUtil {

    private RestBasePathsUtil() {
        throw new UnsupportedOperationException("Util class");
    }

    public static final class Main {

        public static final String MAIN = "/rest/main";

        private static final String WAIT = "/rest/wait";

        public static String getWaitRest(String isWaiting) {
            return WAIT + "/" + isWaiting;
        }
    }

    public static final class InvokeMessage {

        private static final String PROCCES_ID = "procces_id";
        private static final String MESSAGE = "/invoke/process/" + PROCCES_ID + "/message/Message_1";

        public static String getMessageRest(String proccesId) {
            return MESSAGE.replaceFirst(PROCCES_ID, proccesId);
        }
    }
}
