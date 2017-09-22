# retrieval
<h1>solrj + spring</h1>
<h3>The project is a basic tutorials with solrj and spring.  
Just use solrj to seaching data in solr.There are steps which use solrj to operating;  </h3>
<h3>Step1</h3>  
Create a HttpSolrClient(HttpSolrServer may be deprecated);  
<h3>Step2  </h3>  
Create SolrQuery,then use set(String,String) to set params;  
Such as solrQuery.setQuery("key:"+params);  
<h3>Step3  </h3>  
Invoking SolrQery.query(solrQuery),return QueryResponse;  
<h3>Step4  </h3>  
Analyzing the QueryResponse;  

<h2>Running </h2>
This is a Maven project.Use Maven to import the necessary package ,then change the PublicOpionModel.class with your db field as well as in services;
Modifing the url into which your solr's url;<br>
<h4>Working the HTML</h4>
Running the commond : mvn spring-boot:run -Dserver.port=9000;<br>
Listenning in localhost:9000;

