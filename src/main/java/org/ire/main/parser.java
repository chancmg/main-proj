package org.ire.main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.predic8.schema.*;
import com.predic8.wsdl.*;
public class parser {
	private static final String INPUTDIRNAME="rawwsdl";
	private static final String OUTPUTDIRNAME="rawdata";

	public static void main(String[] args) throws IOException {
		File inputdir=new File(INPUTDIRNAME);
		File outputdir=new File(OUTPUTDIRNAME);
		
		File[] files = inputdir.listFiles();
		if (files == null) {
			System.out.println("No files to process");
		} else 
		{
			for (int i = 0; i < files.length; i++) {
				if (!files[i].isDirectory()) {
					System.out.println("Reading wsdl:"+files[i].getName());
					File out=new File(outputdir,files[i].getName()+".txt");
					out.createNewFile();
					processFile(files[i],out);
				}
		}
	}
		
		ExampleApplication e=new ExampleApplication();
		
		e.run();
	}
	
	private static void processFile(File input,File output) throws IOException
	{
		WSDLParser parser = new WSDLParser();
		 
        Definitions defs = parser.parse(input.getPath());
        
        FileWriter out = new FileWriter(output); 
        out.write(defs.getTargetNamespace()+ "\n");
       
        if (defs.getDocumentation() != null) {
            out.write("" + defs.getDocumentation()+ "\n");
        }
      
 
        /* For detailed schema information see the FullSchemaParser.java sample.*/
      
        for (Schema schema : defs.getSchemas()) {
            out.write(schema.getTargetNamespace()+ "\n");
        }
       
        for (Message msg : defs.getMessages()) {
            out.write(msg.getName()+ "\n");
            
            for (Part part : msg.getParts()) {
                out.write(part.getName()+ "\n");
                out.write("" + ((part.getElement() != null) ? part.getElement() : "")+ "\n");
                out.write("" + ((part.getType() != null) ? part.getType() : "" )+ "\n");
             
            }
        }
              
        for (PortType pt : defs.getPortTypes()) {
            out.write("" + pt.getName()+ "\n");
          
            for (Operation op : pt.getOperations()) {
                out.write("" + op.getName()+ "\n");
                out.write(""
                    + ((op.getInput().getName() != null) ? op.getInput().getName() : "")+ "\n");
                out.write(""
                    + op.getInput().getMessage().getQname());
                out.write(""
                    + ((op.getOutput().getName() != null) ? op.getOutput().getName() : "")+ "\n");
                out.write(""
                    + op.getOutput().getMessage().getQname()+ "\n");
                out.write("");
                if (op.getFaults().size() > 0) {
                    for (Fault fault : op.getFaults()) {
                        out.write("" + fault.getName()+ "\n");
                        out.write("" + fault.getMessage().getQname()+ "\n");
                    }
                } else out.write("");
                 
            }
            out.write("");
        }
        out.write("");
 
        for (Binding bnd : defs.getBindings()) {
            out.write("" + bnd.getName()+ "\n");
            out.write("" + bnd.getPortType().getName()+ "\n");
            out.write("" + bnd.getBinding().getProtocol()+ "\n");
            if(bnd.getBinding() instanceof AbstractSOAPBinding) out.write("" + (((AbstractSOAPBinding)bnd.getBinding()).getStyle()+ "\n"));
            out.write("");
            for (BindingOperation bop : bnd.getOperations()) {
                out.write("" + bop.getName()+ "\n");
                if(bnd.getBinding() instanceof AbstractSOAPBinding) {
                    out.write("" + bop.getOperation().getSoapAction()+ "\n");
                    out.write("" + bop.getInput().getBindingElements().get(0).getUse()+ "\n");
                }
            }
            out.write("");
        }
        out.write("");
 
        for (Service service : defs.getServices()) {
            out.write("" + service.getName()+ "\n");
            out.write("");
            for (Port port : service.getPorts()) {
                out.write("" + port.getName()+ "\n");
                out.write("" + port.getBinding().getName()+ "\n");
                out.write("" + port.getAddress().getLocation()
                    + "\n");
            }
        }
        out.write("");
        out.flush();
        out.close();
    }  
	}



