package com.rohan90.majdoor.api;

public class ApiConstants {
    public static final String ENTRY_POINT = "/majdoor";

    /**
     * For healthcheck uris
     */
    public static class Ping {
        private Ping() {
        }

        public static final String BASE = "/ping";
        public static final String INDEX = "/";
//        public static final String DB = "/db";
    }

    public class Tasks {


        private Tasks() {
        }

        public static final String BASE = "/tasks";
        public static final String INDEX = "/";
        public static final String CREATE = BASE + INDEX;
        public static final String GET_ALL = BASE + INDEX;
    }
}
