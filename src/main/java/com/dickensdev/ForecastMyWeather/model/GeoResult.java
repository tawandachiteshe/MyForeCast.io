package com.dickensdev.ForecastMyWeather.model;

public class GeoResult {
    private Bounds bounds;

    public Bounds getBounds() {
        return bounds;
    }

    public class Bounds{
        private Northeast northeast;
        private  Southwest southwest;

        public Northeast getNortheast() {
            return northeast;
        }

        public Southwest getSouthwest() {
            return southwest;
        }

        public class Northeast{
            private  String lat;
            private String lng;

            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }
        }

       public class Southwest{
            private  String lat;
            private String lng;
            public String getLat() {
                return lat;
            }

            public String getLng() {
                return lng;
            }
        }
    }
}

