package pt.iflow.blocks;

import static pt.iflow.blocks.P17040.utils.FileGeneratorUtils.fillAtributtes;
import static pt.iflow.blocks.P17040.utils.FileGeneratorUtils.retrieveSimpleField;
import static pt.iflow.blocks.P17040.utils.FileValidationUtils.isValidDomainValue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import pt.iflow.api.processdata.ProcessData;
import pt.iflow.api.utils.UserInfoInterface;
import pt.iflow.blocks.P17040.utils.ValidationError;

public class BlockP17040ValidateCIND extends BlockP17040Validate {

	public BlockP17040ValidateCIND(int anFlowId, int id, int subflowblockid, String filename) {
		super(anFlowId, id, subflowblockid, filename);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<ValidationError> validate(UserInfoInterface userInfo, ProcessData procData, Connection connection,
			Integer crcId) throws SQLException {

		ArrayList<ValidationError> result = new ArrayList<>();

		//infDiaEnt
		List<Integer> infDiaEntIdList = retrieveSimpleField(connection, userInfo,
				"select infDiaEnt.id from infDiaEnt, comInfDia, conteudo where infDiaEnt.comInfDia_id = comInfDia.id and comInfDia.conteudo_id = conteudo.id and conteudo.crc_id = {0} ",
				new Object[] { crcId });
		
		for(Integer infDiaEnt_id : infDiaEntIdList){
			HashMap<String, Object> infDiaEntValues = fillAtributtes(null, connection, userInfo,
					"select * from infDiaEnt where id = {0} ", new Object[] { infDiaEnt_id });
			
			//idEnt_id
			Integer idEnt_id = (Integer) infDiaEntValues.get("idEnt_id");
			if(retrieveSimpleField(connection, userInfo,
					"select id from infDiaEnt where id = {0} and idEnt_id= {1}",
					new Object[] {infDiaEnt_id, idEnt_id}).size()>1)
				result.add(new ValidationError("EF010", "infDiaEnt", "idEnt_id", infDiaEnt_id, idEnt_id));
			
			//dtAvalRiscoDia
			//PDDia
			BigDecimal PDDia = (BigDecimal) infDiaEntValues.get("PDDia");
			if(PDDia!=null && (PDDia.doubleValue()<0 || PDDia.doubleValue()>100 ))
				result.add(new ValidationError("RE007", "infDiaEnt", "PDDia", infDiaEnt_id, PDDia));
			
			//tpAvalRiscoDia
			String tpAvalRiscoDia = (String) infDiaEntValues.get("tpAvalRiscoDia");
			if(!isValidDomainValue(userInfo, connection, "T_TAR",tpAvalRiscoDia))
				result.add(new ValidationError("RE009", "infDiaEnt", "tpAvalRiscoDia", infDiaEnt_id, tpAvalRiscoDia));
			
			//sistAvalRiscoDia
			String sistAvalRiscoDia = (String) infDiaEntValues.get("sistAvalRiscoDia");
			if(!isValidDomainValue(userInfo, connection, "T_SAR",sistAvalRiscoDia))
				result.add(new ValidationError("RE010", "infDiaEnt", "sistAvalRiscoDia", infDiaEnt_id, sistAvalRiscoDia));
			
			//modIRBDia
			//notacaoCredDia
			
		}
		
		//infDiaInstFin
		List<Integer> infDiaInstFinIdList = retrieveSimpleField(connection, userInfo,
				"select infDiaInstFin.id from infDiaInstFin, comInfDia, conteudo where infDiaInstFin.comInfDia_id = comInfDia.id and comInfDia.conteudo_id = conteudo.id and conteudo.crc_id = {0} ",
				new Object[] { crcId });
		
		for(Integer infDiaInstFin_id : infDiaInstFinIdList){
			HashMap<String, Object> infDiaEntValues = fillAtributtes(null, connection, userInfo,
					"select * from infDiaInstFin where id = {0} ", new Object[] { infDiaInstFin_id });
			
			//dtRefInfDia
			//idCont
			String idCont = (String) infDiaEntValues.get("idCont");
			//idInst
			String idInst = (String) infDiaEntValues.get("idInst");
			if(retrieveSimpleField(connection, userInfo,
					"select id from infDiaInstFin where idCont = ''{0}'' and idInst= ''{1}'' and id = {2}",
					new Object[] {idCont, idInst}).size()>1)
				result.add(new ValidationError("EF015", "infDiaInstFin", "idInst", infDiaInstFin_id, idInst));
			//TAADia
			//capitalVivo
			BigDecimal capitalVivo = (BigDecimal) infDiaEntValues.get("capitalVivo");
			if(capitalVivo==null)
				result.add(new ValidationError("ID009", "infDiaInstFin", "capitalVivo", infDiaInstFin_id));
			if(capitalVivo!=null && capitalVivo.compareTo(BigDecimal.ZERO)==-1)
				result.add(new ValidationError("ID008", "infDiaInstFin", "capitalVivo", infDiaInstFin_id, capitalVivo));
			
			//entInstDia
			List<Integer> entInstDiaIdList = retrieveSimpleField(connection, userInfo,
					"select id from entInstDia where entInstDia.infDiaInstFin_id = {0} ",
					new Object[] { infDiaInstFin_id });
			
			for(Integer entInstDia_id: entInstDiaIdList){
				HashMap<String, Object> entInstDiaValues = fillAtributtes(null, connection, userInfo,
						"select * from entInstDia where id = {0} ", new Object[] { entInstDia_id });
				
				//montTotDia				
				BigDecimal montTotDia = (BigDecimal) entInstDiaValues.get("montTotDia");
				if(montTotDia==null)
					result.add(new ValidationError("ID009", "entInstDiaValues", "montTotDia", entInstDia_id));
				if(montTotDia!=null && montTotDia.compareTo(BigDecimal.ZERO)==-1)
					result.add(new ValidationError("ID008", "entInstDiaValues", "montTotDia", entInstDia_id, montTotDia));
				
				//montVencDia
				BigDecimal montVencDia = (BigDecimal) entInstDiaValues.get("montVencDia");
				if(montVencDia==null)
					result.add(new ValidationError("ID009", "entInstDiaValues", "montVencDia", entInstDia_id));
				if(montVencDia!=null && montVencDia.compareTo(BigDecimal.ZERO)==-1)
					result.add(new ValidationError("ID008", "entInstDiaValues", "montVencDia", entInstDia_id, montVencDia));
				
				//montAbAtvDia
				BigDecimal montAbAtvDia = (BigDecimal) entInstDiaValues.get("montAbAtvDia");
				if(montAbAtvDia==null)
					result.add(new ValidationError("ID009", "entInstDiaValues", "montAbAtvDia", entInstDia_id));
				if(montAbAtvDia!=null && montAbAtvDia.compareTo(BigDecimal.ZERO)==-1)
					result.add(new ValidationError("ID008", "entInstDiaValues", "montAbAtvDia", entInstDia_id, montAbAtvDia));
				
				//montPotRevDia
				BigDecimal montPotRevDia = (BigDecimal) entInstDiaValues.get("montPotRevDia");
				if(montPotRevDia==null)
					result.add(new ValidationError("ID009", "entInstDiaValues", "montPotRevDia", entInstDia_id));
				if(montPotRevDia!=null && montPotRevDia.compareTo(BigDecimal.ZERO)==-1)
					result.add(new ValidationError("ID008", "entInstDiaValues", "montPotRevDia", entInstDia_id, montPotRevDia));
				
				//montPotIrrevDia
				BigDecimal montPotIrrevDia = (BigDecimal) entInstDiaValues.get("montPotIrrevDia");
				if(montPotIrrevDia==null)
					result.add(new ValidationError("ID009", "entInstDiaValues", "montPotIrrevDia", entInstDia_id));
				if(montPotIrrevDia!=null && montPotIrrevDia.compareTo(BigDecimal.ZERO)==-1)
					result.add(new ValidationError("ID008", "entInstDiaValues", "montPotIrrevDia", entInstDia_id, montPotIrrevDia));
				
				//tpEventDia
				String tpEventDia = (String) entInstDiaValues.get("tpEventDia");
				if(!isValidDomainValue(userInfo, connection, "T_TEV",tpEventDia))
					result.add(new ValidationError("ID010", "entInstDiaValues", "tpEventDia", entInstDia_id, tpEventDia));
				
				if(montTotDia!=null && montVencDia!=null && montAbAtvDia!=null && montPotRevDia!=null && montPotIrrevDia!=null && 
						montTotDia.doubleValue()<=0 && montVencDia.doubleValue()<=0 && montAbAtvDia.doubleValue()<=0 && montPotRevDia.doubleValue()<=0 && montPotIrrevDia.doubleValue()<=0 &&
						(StringUtils.equals(tpEventDia, "001") || StringUtils.equals(tpEventDia, "003") || StringUtils.equals(tpEventDia, "005")))
					result.add(new ValidationError("ID012", "entInstDiaValues", "tpEventDia", entInstDia_id, tpEventDia));
				
				if(montVencDia!=null && montAbAtvDia!=null && montVencDia.doubleValue()<=0 && montAbAtvDia.doubleValue()<=0 &&
						( StringUtils.equals(tpEventDia, "003")))
					result.add(new ValidationError("ID013", "entInstDiaValues", "tpEventDia", entInstDia_id, tpEventDia));
				
				if(montTotDia!=null && montVencDia!=null && montAbAtvDia!=null && montPotRevDia!=null && montPotIrrevDia!=null && 
						montTotDia.doubleValue()!=0 && montVencDia.doubleValue()!=0 && montAbAtvDia.doubleValue()!=0 && montPotRevDia.doubleValue()!=0 && montPotIrrevDia.doubleValue()!=0 &&
						(StringUtils.equals(tpEventDia, "002")))
					result.add(new ValidationError("ID014", "entInstDiaValues", "tpEventDia", entInstDia_id, tpEventDia));
				
				if(montVencDia!=null && montAbAtvDia!=null && montVencDia.doubleValue()>=0 && montAbAtvDia.doubleValue()>=0 &&
						( StringUtils.equals(tpEventDia, "004")))
					result.add(new ValidationError("ID013", "entInstDiaValues", "tpEventDia", entInstDia_id, tpEventDia));
				
				//tpRespDia
				String tpRespDia = (String) entInstDiaValues.get("tpRespDia");
				if(!StringUtils.equals(tpRespDia, "002") && !StringUtils.equals(tpRespDia, "003"))
					result.add(new ValidationError("ID006", "entInstDiaValues", "tpRespDia", entInstDia_id, tpRespDia));
				if(StringUtils.isBlank(tpRespDia))
					result.add(new ValidationError("ID015", "entInstDiaValues", "tpRespDia", entInstDia_id));
				if(!isValidDomainValue(userInfo, connection, "T_TRS",tpRespDia))
					result.add(new ValidationError("ID016", "entInstDiaValues", "tpRespDia", entInstDia_id, tpRespDia));
			}
		}
		return result;
	}
}
