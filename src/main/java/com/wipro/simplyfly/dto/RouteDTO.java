package com.wipro.simplyfly.dto;

public class RouteDTO {
	
	    private int id;

	    private String source;

	    private String destination;

	    private Double distance;

	    private String estimatedDuration;

		public RouteDTO() {
			super();
		}

		public RouteDTO(int id, String source, String destination, Double distance, String estimatedDuration) {
			super();
			this.id = id;
			this.source = source;
			this.destination = destination;
			this.distance = distance;
			this.estimatedDuration = estimatedDuration;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getDestination() {
			return destination;
		}

		public void setDestination(String destination) {
			this.destination = destination;
		}

		public Double getDistance() {
			return distance;
		}

		public void setDistance(Double distance) {
			this.distance = distance;
		}

		public String getEstimatedDuration() {
			return estimatedDuration;
		}

		public void setEstimatedDuration(String estimatedDuration) {
			this.estimatedDuration = estimatedDuration;
		}

}
