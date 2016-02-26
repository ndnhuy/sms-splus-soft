package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import vn.com.splussoftware.sms.model.entity.DataTemplateEntity;
import vn.com.splussoftware.sms.model.entity.EntitiesRoleEntity;
import vn.com.splussoftware.sms.model.entity.PhaseEntity;
import vn.com.splussoftware.sms.model.entity.PhaseTimeEntity;
import vn.com.splussoftware.sms.model.entity.RelationshipEntity;
import vn.com.splussoftware.sms.model.entity.TicketEntity;
import vn.com.splussoftware.sms.model.entity.TicketInfoEntity;
import vn.com.splussoftware.sms.model.entity.TicketTimeEntity;


public interface TicketService {
	List<TicketEntity> getAllTicket();
	List<DataTemplateEntity> getAllDataTemplate();
	List<EntitiesRoleEntity> getAllEntitiesRole();
	List<PhaseEntity> getAllPhase();
	List<PhaseTimeEntity> getAllPhaseTime();
	List<RelationshipEntity> getAllRelationship();
	List<TicketInfoEntity> getAllTicketInfo();
	List<TicketTimeEntity> getAllTicketTime();
	
	TicketEntity getTicketById(long id);
	DataTemplateEntity getDataTemplateById(long id);
	EntitiesRoleEntity getEntitiesRoleById(long id);
	PhaseEntity getPhaseById(long id);
	PhaseTimeEntity getPhaseTimeById(long id);
	RelationshipEntity getRelationshipById(long id);
	TicketInfoEntity getTicketInfoById(long id);
	TicketTimeEntity getTicketTimeById(long id);
	
	TicketEntity updateTicket(TicketEntity ticket);
	DataTemplateEntity updateDataTemplate(DataTemplateEntity dataTemplate);
	EntitiesRoleEntity updateEntitiesRole(EntitiesRoleEntity entitiesRole);
	PhaseEntity updatePhase(PhaseEntity phase);
	PhaseTimeEntity updatePhaseTime(PhaseTimeEntity phaseTime);
	RelationshipEntity updateRelationship(RelationshipEntity relationship);
	TicketInfoEntity updateTicketInfo(TicketInfoEntity ticketInfo);
	TicketTimeEntity updateTicketTime(TicketTimeEntity ticketTime);
	
	TicketEntity createTicket(TicketEntity ticket);
	DataTemplateEntity createDataTemplate(DataTemplateEntity dataTemplate);
	EntitiesRoleEntity createEntitiesRole(EntitiesRoleEntity entitiesRole);
	PhaseEntity createPhase(PhaseEntity phase);
	PhaseTimeEntity createPhaseTime(PhaseTimeEntity phaseTime);
	RelationshipEntity createRelationship(RelationshipEntity relationship);
	TicketInfoEntity createTicketInfo(TicketInfoEntity ticketInfo);
	TicketTimeEntity createTicketTime(TicketTimeEntity ticketTime);
	
	TicketEntity deleteTicketById(long id);
	DataTemplateEntity deleteDataTemplateById(long id);
	EntitiesRoleEntity deleteEntitiesRoleById(long id);
	PhaseEntity deletePhaseById(long id);
	PhaseTimeEntity deletePhaseTimeById(long id);
	RelationshipEntity deleteRelationshipById(long id);
	TicketInfoEntity deleteTicketInfoById(long id);
	TicketTimeEntity deleteTicketTimeById(long id);
	
	List<PhaseEntity> getPhasesPrev(long id);
	List<PhaseEntity> getPhasesForw(long id);
	List<RelationshipEntity> getRelationPrev(long id);
	List<RelationshipEntity> getRelationForw(long id);
}
