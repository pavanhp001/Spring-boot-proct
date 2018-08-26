package com.AL.ui.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ChannelLineupVO  implements Serializable
{

	private final static long serialVersionUID = -1L;

	private Integer id;

	private Response response;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Response getResponse() {
		if (response == null) {
			response = new Response();
		}
		return this.response;
	}

	public void setResponse(Response value) {
		this.response = value;
	}

	@Override
	public String toString() {
		return "ChannelLineupVO [Id=" + id + ", response=" + response + "]";
	}


	public static class Response implements Serializable
	{

		private final static long serialVersionUID = -1L;

		
		private Status status;

		
		private Data data;

		/**
		 * Gets the value of the status property.
		 * 
		 * @return
		 *     possible object is
		 *     {@link ChannelLineupVO.Response.Status }
		 *     
		 */
		public Status getStatus() {
			return status;
		}

		/**
		 * Sets the value of the status property.
		 * 
		 * @param value
		 *     allowed object is
		 *     {@link ChannelLineupVO.Response.Status }
		 *     
		 */
		public void setStatus(Status value) {
			this.status = value;
		}

		/**
		 * Gets the value of the resData property.
		 * 
		 * @return
		 *     possible object is
		 *     {@link ChannelLineupVO.Response.ResData }
		 *     
		 */
		public Data getData() {
			if (data == null) {
				data = new Data();
			}
			return this.data;
		}

		/**
		 * Sets the value of the data property.
		 * 
		 * @param value
		 *     allowed object is
		 *     {@link ChannelLineupVO.Response.Data }
		 *     
		 */
		public void setData(Data value) {
			this.data = value;
		}
		
		@Override
		public String toString() {
			return "Response [status=" + status + ", data=" + data + "]";
		}

		public static class Data implements Serializable
		{

			private final static long serialVersionUID = -1L;

			
			private Provider provider;

			
			private Channels channels;

			/**
			 * Gets the value of the provider property.
			 * 
			 * @return
			 *     possible object is
			 *     {@link ChannelLineupVO.Provider }
			 *     
			 */
			public Provider getProvider() {
				return provider;
			}

			/**
			 * Sets the value of the provider property.
			 * 
			 * @param value
			 *     allowed object is
			 *     {@link ChannelLineupVO.Provider }
			 *     
			 */
			public void setProvider(Provider value) {
				this.provider = value;
			}

			/**
			 * Gets the value of the channels property.
			 * 
			 * @return
			 *     possible object is
			 *     {@link ChannelLineupVO.Channels }
			 *     
			 */
			public Channels getChannels() {
				return channels;
			}

			/**
			 * Sets the value of the channels property.
			 * 
			 * @param value
			 *     allowed object is
			 *     {@link ChannelLineupVO.Channels }
			 *     
			 */
			public void setChannels(Channels value) {
				this.channels = value;
			}

			@Override
			public String toString() {
				return "Data [provider=" + provider + ", channels=" + channels
						+ "]";
			}

			public static class Channels implements Serializable
			{

				@Override
				public String toString() {
					return "Channels [channel=" + channel + "]";
				}


				private final static long serialVersionUID = -1L;

				
				private List<Channel> channel;

				public void setChannel(List<Channel> channel) {
					this.channel = channel;
				}


				/**
				 * Gets the value of the channels property.
				 * 
				 */
				public List<Channel> getChannel() {
					if (channel == null) {
						channel = new ArrayList<Channel>();
					}
					return this.channel;
				}

				public static class Channel implements Serializable
				{

					private final static long serialVersionUID = -1L;

					
					private String id;

					
					private String name;

					
					private String category;

					
					private String definition;

					
					private String logoURL;


					/**
					 * Gets the value of the category property.
					 * 
					 * @return
					 *     possible object is
					 *     {@link String }
					 *     
					 */
					public String getCategory() {
						return category;
					}

					/**
					 * Sets the value of the category property.
					 * 
					 * @param value
					 *     allowed object is
					 *     {@link String }
					 *     
					 */
					public void setCategory(String value) {
						this.category = value;
					}


					/**
					 * Gets the value of the logoURL property.
					 * 
					 * @return
					 *     possible object is
					 *     {@link ChannelLineupVO.Channels.Channel.LogoURL }
					 *     
					 */
					public String getLogoURL() {
						return logoURL;
					}

					/**
					 * Sets the value of the logoURL property.
					 * 
					 * @param value
					 *     allowed object is
					 *     {@link ChannelLineupVO.Channels.Channel.LogoURL }
					 *     
					 */
					public void setLogoURL(String value) {
						this.logoURL = value;
					}

					/**
					 * Gets the value of the id property.
					 * 
					 * @return
					 *     possible object is
					 *     {@link String }
					 *     
					 */
					public String getId() {
						return id;
					}

					/**
					 * Sets the value of the id property.
					 * 
					 * @param value
					 *     allowed object is
					 *     {@link String }
					 *     
					 */
					public void setId(String value) {
						this.id = value;
					}

					public String getName() {
						return name;
					}

					public void setName(String name) {
						this.name = name;
					}

					public String getDefinition() {
						return definition;
					}

					public void setDefinition(String definition) {
						this.definition = definition;
					}
					
					@Override
					public String toString() {
						return "{\"id\" : \"" + id + "\", \"name\" : \"" + name
								+ "\", \"category\" : \"" + category + "\", \"definition\" : \""
								+ definition + "\", \"logoURL\" : \"" + logoURL + "\"}";
					}

				}

			}



			public static class Provider implements Serializable
			{

				private final static long serialVersionUID = -1L;

				
				private String name;

				
				private Packages packages;

				
				private String id;

				/**
				 * Gets the value of the name property.
				 * 
				 * @return
				 *     possible object is
				 *     {@link String }
				 *     
				 */
				public String getName() {
					return name;
				}

				/**
				 * Sets the value of the name property.
				 * 
				 * @param value
				 *     allowed object is
				 *     {@link String }
				 *     
				 */
				public void setName(String value) {
					this.name = value;
				}

				/**
				 * Gets the value of the packages property.
				 * 
				 * @return
				 *     possible object is
				 *     {@link ChannelLineupVO.Provider.Packages }
				 *     
				 */
				public Packages getPackages() {
					return packages;
				}

				/**
				 * Sets the value of the packages property.
				 * 
				 * @param value
				 *     allowed object is
				 *     {@link ChannelLineupVO.Provider.Packages }
				 *     
				 */
				public void setPackages(Packages value) {
					this.packages = value;
				}

				/**
				 * Gets the value of the id property.
				 * 
				 * @return
				 *     possible object is
				 *     {@link String }
				 *     
				 */
				public String getId() {
					return id;
				}

				/**
				 * Sets the value of the id property.
				 * 
				 * @param value
				 *     allowed object is
				 *     {@link String }
				 *     
				 */
				public void setId(String value) {
					this.id = value;
				}

				@Override
				public String toString() {
					return "Provider [name=" + name + ", packages=" + packages + ", id="
							+ id + "]";
				}
				
				public static class Packages implements Serializable
				{


					private final static long serialVersionUID = -1L;

					
					private ArrayList<Package> cluPackage;



					public ArrayList<Package> getCluPackage() {
						if (cluPackage == null) {
							cluPackage = new ArrayList<Package>();
						}
						return this.cluPackage;
					}



					public void setCluPackage(ArrayList<Package> cluPackage) {
						this.cluPackage = cluPackage;
					}

					@Override
					public String toString() {
						return "Packages [cluPackage=" + cluPackage + "]";
					}

					public static class Package implements Serializable
					{


						private final static long serialVersionUID = -1L;

						
						private String name;

						
						private String region;

						
						private List<Category> category;

						public List<Category> getCategory() {
							if (category == null) {
								category = new ArrayList<Category>();
							}
							return this.category;
						}

						public void setCategory(List<Category> category) {
							this.category = category;
						}


						/**
						 * Gets the value of the name property.
						 * 
						 * @return
						 *     possible object is
						 *     {@link String }
						 *     
						 */
						public String getName() {
							return name;
						}

						/**
						 * Sets the value of the name property.
						 * 
						 * @param value
						 *     allowed object is
						 *     {@link String }
						 *     
						 */
						public void setName(String value) {
							this.name = value;
						}

						public String getRegion() {
							return region;
						}

						public void setRegion(String region) {
							this.region = region;
						}

						@Override
						public String toString() {
							return "Package [name=" + name + ", region="
									+ region + "  categoryList =" +category+"]";
						}
						

						public static class Category implements Serializable
						{

							private final static long serialVersionUID = -1L;

							private String name;

							private ChannelRefs channelRefs;

							/**
							 * Gets the value of the name property.
							 * 
							 * @return
							 *     possible object is
							 *     {@link String }
							 *     
							 */
							public String getName() {
								return name;
							}

							/**
							 * Sets the value of the name property.
							 * 
							 * @param value
							 *     
							 */
							public void setName(String value) {
								this.name = value;
							}

							/**
							 * Gets the value of the channelRefs property.
							 * 
							 *     
							 */
							public ChannelRefs getChannelRefs() {
								if (channelRefs == null) {
									channelRefs = new ChannelRefs();
								}
								return this.channelRefs;
							}

							/**
							 * Sets the value of the channelRefs property.
							 * 
							 *     
							 */
							public void setChannelRefs(ChannelRefs channelRefs) {
								this.channelRefs = channelRefs;
							}

							public void setChannels(ChannelRefs value) {
								this.channelRefs = value;
							}
						}



						public static class ChannelRefs implements Serializable
						{

							private final static long serialVersionUID = -1L;

							
							private List<ChannelId> channelId;

							public void setChannelId(List<ChannelId> channelId) {
								this.channelId = channelId;
							}


							public List<ChannelId> getChannelId() {
								if (channelId == null) {
									channelId = new ArrayList<ChannelId>();
								}
								return this.channelId;
							}

							@Override
							public String toString() {
								return "ChannelRefs [channelID=" + channelId
										+ "]";
							}

							public static class ChannelId implements Serializable
							{

								private final static long serialVersionUID = -1L;

								
								private String ref;

								/**
								 * Gets the value of the ref property.
								 * 
								 * @return
								 *     possible object is
								 *     {@link String }
								 *     
								 */
								public String getRef() {
									return ref;
								}

								/**
								 * Sets the value of the ref property.
								 * 
								 * @param value
								 *     allowed object is
								 *     {@link String }
								 *     
								 */
								public void setRef(String value) {
									this.ref = value;
								}
								
								@Override
								public String toString() {
									return "RefChannel [ref=" + ref + "]";
								}

							}

						}

					}

				}

			}
		}


		/**
		 * <p>Java class for anonymous complex type.
		 * 
		 * <p>The following schema fragment specifies the expected content contained within this class.
		 * 
		 * <pre>
		 * &lt;complexType>
		 *   &lt;complexContent>
		 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
		 *       &lt;sequence>
		 *         &lt;element name="msg" type="{http://www.w3.org/2001/XMLSchema}string"/>
		 *       &lt;/sequence>
		 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}int" />
		 *     &lt;/restriction>
		 *   &lt;/complexContent>
		 * &lt;/complexType>
		 * </pre>
		 * 
		 * 
		 */
		public static class Status implements Serializable
		{


			private final static long serialVersionUID = -1L;

			
			private String msg;

			
			private String code;

			/**
			 * Gets the value of the msg property.
			 * 
			 * @return
			 *     possible object is
			 *     {@link String }
			 *     
			 */
			public String getMsg() {
				return msg;
			}

			/**
			 * Sets the value of the msg property.
			 * 
			 * @param value
			 *     allowed object is
			 *     {@link String }
			 *     
			 */
			public void setMsg(String value) {
				this.msg = value;
			}

			/**
			 * Gets the value of the code property.
			 * 
			 * @return
			 *     possible object is
			 *     {@link Integer }
			 *     
			 */
			public String getCode() {
				return code;
			}

			/**
			 * Sets the value of the code property.
			 * 
			 * @param value
			 *     allowed object is
			 *     {@link Integer }
			 *     
			 */
			public void setCode(String value) {
				this.code = value;
			}
			
			@Override
			public String toString() {
				return "Status [msg=" + msg + ", code=" + code + "]";
			}

		}

	}

}

