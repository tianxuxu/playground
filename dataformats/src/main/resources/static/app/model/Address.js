Ext.define("DF.model.Address",
{
  extend : "Ext.data.Model",
  
  fields : [ "firstName", {
    name : "lastName",
    validators : [ {
      type : "presence"
    } ]
  }, {
    name : "email",
    validators : [ {
      type : "email"
    } ]
  }, "department", {
    name : "id",
    convert : null
  } ]
});