/*
 * KML.java
 * Last modified on 04-06-2014 11:02-0400 by brianhmayo
 *
 * Copyright (c) 2014 SEPTA.  All rights reserved.
 */

package org.septa.android.app.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class KMLModel {
    private Document document;

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public void createDocument() {
        this.document = new Document();
    }

    public static class Document {
        private String name;
        private Style style;
        private Folder folder;
        private List<Placemark> placemarks;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Style getStyle() {
            return style;
        }

        public void setStyle(Style style) {
            this.style = style;
        }

        public void createStyle() {
            this.style = new Style();
        }

        public Folder getFolder() {
            return folder;
        }

        public void setFolder(Folder folder) {
            this.folder = folder;
        }

        public List<Placemark> getPlacemarks() {
            return placemarks;
        }

        public void setPlacemarks(List<Placemark>placemarks) {
            this.placemarks = placemarks;
        }

        public List<Placemark> createPlacemarks() {
            this.placemarks = new ArrayList<Placemark>();

            return placemarks;
        }

        public static class Style {
            private String id;

            private LabelStyle labelStyle;
            private LineStyle lineStyle;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public LabelStyle getLabelStyle() {
                return labelStyle;
            }

            public void setLabelStyle(LabelStyle labelStyle) {
                this.labelStyle = labelStyle;
            }

            public LineStyle getLineStyle() {
                return lineStyle;
            }

            public void setLineStyle(LineStyle lineStyle) {
                this.lineStyle = lineStyle;
            }

            public LineStyle createLineStyle() {
                this.lineStyle = new LineStyle();

                return lineStyle;
            }

            public static class LabelStyle {
                private String color;
                private int scale;

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public int getScale() {
                    return scale;
                }

                public void setScale(int scale) {
                    this.scale = scale;
                }
            }

            public static class LineStyle {
                private String color;
                private String style;

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getStyle() {
                    return style;
                }

                public void setStyle(String style) {
                    this.style = style;
                }
            }
        }

        static class Folder {
            private String id;

            private String name;
            private boolean open;
            private Snippet snippet;
            private List<Placemark> placemarks;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean getOpen() {
                return open;
            }

            public void setOpen(boolean open) {
                this.open = open;
            }

            public Snippet getSnippet() {
                return snippet;
            }

            public void setSnippet(Snippet snippet) {
                this.snippet = snippet;
            }

            public List getPlacemarks() {
                return placemarks;
            }
        }

        static class Snippet {
            private int maxLines;

            private String text;

            public int getMaxLines() {
                return maxLines;
            }

            public void setMaxLines(int maxLines) {
                this.maxLines = maxLines;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

            public String getSnippet() {
                return text;
            }
        }

        public static class Placemark {
            private String name;
            private Snippet snippet;
            private String styleUrl;
            private MultiGeometry multiGeometry;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Snippet getSnippet() {
                return snippet;
            }

            public void setSnippet(Snippet snippet) {
                this.snippet = snippet;
            }

            public String getStyleUrl() {
                return styleUrl;
            }

            public void setStyleUrl(String styleUrl) {
                this.styleUrl = styleUrl;
            }

            public MultiGeometry getMultiGeometry() {
                return multiGeometry;
            }

            public void setMultiGeometry(MultiGeometry multiGeometry) {
                this.multiGeometry = multiGeometry;
            }

            public MultiGeometry createMultiGeometry() {
                this.multiGeometry = new MultiGeometry();

                return multiGeometry;
            }
        }

        public static class MultiGeometry {
            private LineString lineString;

            public LineString getLineString() {
                return lineString;
            }

            public void setLineString(LineString lineString) {
                this.lineString = lineString;
            }

            public LineString createLineString() {
                this.lineString = new LineString();

                return lineString;
            }

            public static class LineString {
                private int tessellate;
                private String rawCoordinateString;

                private Coordinate coordinate;

                public int getTessellate() {
                    return tessellate;
                }

                public void setTessellate(int tessellate) {
                    this.tessellate = tessellate;
                }

                public String getRawCoordinateString() {
                    return rawCoordinateString;
                }

                public void setRawCoordinateString(String rawCoordinateString) {
                    this.rawCoordinateString = rawCoordinateString;
                }

                public Coordinate getCoordinate() {
                    return coordinate;
                }

                public void setCoordinates(Coordinate coordinate) {
                    this.coordinate = coordinate;
                }

                public void processRawCoordinatesString() {
                    if (rawCoordinateString != null && !rawCoordinateString.isEmpty()) {
                        String[] splitRawCoordinate = this.rawCoordinateString.split(" ");

                        for (String coordinatePairPlus : splitRawCoordinate) {
                            String[] coordinateParts = coordinatePairPlus.split(",");
                            if (coordinateParts.length != 3) {
                                Log.d(KMLModel.class.getName(), "an error has occured processing the raw coordinates");
                            } else {
                                coordinate = new Coordinate();
                                coordinate.setLatitude(Double.parseDouble(coordinateParts[0]));
                                coordinate.setLongitude(Double.parseDouble(coordinateParts[1]));
                                coordinate.setNotsure(Integer.parseInt(coordinateParts[2]));
                            }
                        }
                    }
                }

                public static class Coordinate {
                    private double latitude;
                    private double longitude;
                    private int notsure;

                    public double getLatitude() {
                        return latitude;
                    }

                    public void setLatitude(double latitude) {
                        this.latitude = latitude;
                    }

                    public double getLongitude() {
                        return longitude;
                    }

                    public void setLongitude(double longitude) {
                        this.longitude = longitude;
                    }

                    public int getNotsure() {
                        return notsure;
                    }

                    public void setNotsure(int notsure) {
                        this.notsure = notsure;
                    }
                }
            }
        }

    }
}
