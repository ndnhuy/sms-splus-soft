package vn.com.splussoftware.sms.utils.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.splussoftware.sms.model.entity.DataTemplateEntity;
import vn.com.splussoftware.sms.model.entity.EntitiesRoleEntity;
import vn.com.splussoftware.sms.model.entity.PhaseEntity;
import vn.com.splussoftware.sms.model.entity.PhaseTimeEntity;
import vn.com.splussoftware.sms.model.entity.RelationshipEntity;
import vn.com.splussoftware.sms.model.entity.TicketEntity;
import vn.com.splussoftware.sms.model.entity.TicketInfoEntity;
import vn.com.splussoftware.sms.model.entity.TicketTimeEntity;
import vn.com.splussoftware.sms.model.repository.DataTemplateRepository;
import vn.com.splussoftware.sms.model.repository.EntityRoleRepository;
import vn.com.splussoftware.sms.model.repository.PhaseRepository;
import vn.com.splussoftware.sms.model.repository.PhaseTimeRepository;
import vn.com.splussoftware.sms.model.repository.RelationshipRepository;
import vn.com.splussoftware.sms.model.repository.TicketInfoRepository;
import vn.com.splussoftware.sms.model.repository.TicketRepository;
import vn.com.splussoftware.sms.model.repository.TicketTimeRepository;

@Service
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private DataTemplateRepository dataTemplateRepository;
	
	@Autowired
	private EntityRoleRepository entityRoleRepository;
	
	@Autowired
	private PhaseRepository phaseRepository;
	
	@Autowired
	private PhaseTimeRepository phaseTimeRepository;
	
	@Autowired
	private RelationshipRepository relationshipRepository;
	
	@Autowired
	private TicketInfoRepository ticketInfoRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private TicketTimeRepository ticketTimeRepository;
	
	@Override
	public List<TicketEntity> getAllTicket() {
		return ticketRepository.findAll();
	}

	@Override
	public List<DataTemplateEntity> getAllDataTemplate() {
		return dataTemplateRepository.findAll();
	}

	@Override
	public List<EntitiesRoleEntity> getAllEntitiesRole() {
		return entityRoleRepository.findAll();
	}

	@Override
	public List<PhaseEntity> getAllPhase() {
		return phaseRepository.findAll();
	}

	@Override
	public List<PhaseTimeEntity> getAllPhaseTime() {
		return phaseTimeRepository.findAll();
	}

	@Override
	public List<RelationshipEntity> getAllRelationship() {
		return relationshipRepository.findAll();
	}

	@Override
	public List<TicketInfoEntity> getAllTicketInfo() {
		return ticketInfoRepository.findAll();
	}

	@Override
	public List<TicketTimeEntity> getAllTicketTime() {
		return ticketTimeRepository.findAll();
	}

	@Override
	public TicketEntity getTicketById(long id) {
		return ticketRepository.getOne(id);
	}

	@Override
	public DataTemplateEntity getDataTemplateById(long id) {
		return dataTemplateRepository.getOne(id);
	}

	@Override
	public EntitiesRoleEntity getEntitiesRoleById(long id) {
		return entityRoleRepository.findOne(id);
	}

	@Override
	public PhaseEntity getPhaseById(long id) {
		return phaseRepository.findOne(id);
	}

	@Override
	public PhaseTimeEntity getPhaseTimeById(long id) {
		return phaseTimeRepository.getOne(id);
	}

	@Override
	public RelationshipEntity getRelationshipById(long id) {
		return relationshipRepository.getOne(id);
	}

	@Override
	public TicketInfoEntity getTicketInfoById(long id) {
		return ticketInfoRepository.getOne(id);
	}

	@Override
	public TicketTimeEntity getTicketTimeById(long id) {
		return ticketTimeRepository.getOne(id);
	}

	@Override
	public TicketEntity updateTicket(TicketEntity ticket) {
		if	(ticket.getID() == 0){
			logger.warn("No id apply, updateTicket func will return a null");
			return null;
		}
		TicketEntity iTicket = ticketRepository.getOne(ticket.getID());
		if	(iTicket == null){
			logger.warn("ID not found, updateTicket func will return a null");
			return null;
		}	
		return ticketRepository.saveAndFlush(ticket);
	}

	@Override
	public DataTemplateEntity updateDataTemplate(DataTemplateEntity dataTemplate) {
		if	(dataTemplate.getID() == 0){
			logger.warn("No id apply, updateDataTemplate func will return a null");
			return null;
		}
		DataTemplateEntity iObject = dataTemplateRepository.getOne(dataTemplate.getID());
		if	(iObject == null){
			logger.warn("ID not found, updateDataTemplate func will return a null");
			return null;
		}	
		return dataTemplateRepository.saveAndFlush(dataTemplate);
	}

	@Override
	public EntitiesRoleEntity updateEntitiesRole(EntitiesRoleEntity entitiesRole) {
		if	(entitiesRole.getID() == 0){
			logger.warn("No id apply, updateEntitiesRole func will return a null");
			return null;
		}
		EntitiesRoleEntity iObject = entityRoleRepository.getOne(entitiesRole.getID());
		if	(iObject == null){
			logger.warn("ID not found, updateEntitiesRole func will return a null");
			return null;
		}	
		return entityRoleRepository.saveAndFlush(entitiesRole);
	}

	@Override
	public PhaseEntity updatePhase(PhaseEntity phase) {
		if	(phase.getID() == 0){
			logger.warn("No id apply, updatePhase func will return a null");
			return null;
		}
		PhaseEntity iObject = phaseRepository.getOne(phase.getID());
		if	(iObject == null){
			logger.warn("ID not found, updatePhase func will return a null");
			return null;
		}	
		return phaseRepository.saveAndFlush(phase);
	}

	@Override
	public PhaseTimeEntity updatePhaseTime(PhaseTimeEntity phaseTime) {
		if	(phaseTime.getID() == 0){
			logger.warn("No id apply, updatePhaseTime func will return a null");
			return null;
		}
		PhaseTimeEntity iObject = phaseTimeRepository.getOne(phaseTime.getID());
		if	(iObject == null){
			logger.warn("ID not found, updatePhaseTime func will return a null");
			return null;
		}	
		return phaseTimeRepository.saveAndFlush(phaseTime);
	}

	@Override
	public RelationshipEntity updateRelationship(RelationshipEntity relationship) {
		if	(relationship.getID() == 0){
			logger.warn("No id apply, updateRelationship func will return a null");
			return null;
		}
		RelationshipEntity iObject = relationshipRepository.getOne(relationship.getID());
		if	(iObject == null){
			logger.warn("ID not found, updateRelationship func will return a null");
			return null;
		}	
		return relationshipRepository.saveAndFlush(relationship);
	}

	@Override
	public TicketInfoEntity updateTicketInfo(TicketInfoEntity ticketInfo) {
		if	(ticketInfo.getID() == 0){
			logger.warn("No id apply, updateTicketInfo func will return a null");
			return null;
		}
		TicketInfoEntity iObject = ticketInfoRepository.getOne(ticketInfo.getID());
		if	(iObject == null){
			logger.warn("ID not found, updateTicketInfo func will return a null");
			return null;
		}	
		return ticketInfoRepository.saveAndFlush(ticketInfo);
	}

	@Override
	public TicketTimeEntity updateTicketTime(TicketTimeEntity ticketTime) {
		if	(ticketTime.getID() == 0){
			logger.warn("No id apply, updateTicketTime func will return a null");
			return null;
		}
		TicketTimeEntity iObject = ticketTimeRepository.getOne(ticketTime.getID());
		if	(iObject == null){
			logger.warn("ID not found, updateTicketTime func will return a null");
			return null;
		}	
		return ticketTimeRepository.saveAndFlush(ticketTime);
	}

	@Override
	public TicketEntity createTicket(TicketEntity ticket) {
		if	(ticket.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return ticketRepository.saveAndFlush(ticket);
	}

	@Override
	public DataTemplateEntity createDataTemplate(DataTemplateEntity dataTemplate) {
		if	(dataTemplate.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return dataTemplateRepository.saveAndFlush(dataTemplate);
	}

	@Override
	public EntitiesRoleEntity createEntitiesRole(EntitiesRoleEntity entitiesRole) {
		if	(entitiesRole.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return entityRoleRepository.saveAndFlush(entitiesRole);
	}

	@Override
	public PhaseEntity createPhase(PhaseEntity phase) {
		if	(phase.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return phaseRepository.saveAndFlush(phase);
	}

	@Override
	public PhaseTimeEntity createPhaseTime(PhaseTimeEntity phaseTime) {
		if	(phaseTime.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return phaseTimeRepository.saveAndFlush(phaseTime);
	}

	@Override
	public RelationshipEntity createRelationship(RelationshipEntity relationship) {
		if	(relationship.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return relationshipRepository.saveAndFlush(relationship);
	}

	@Override
	public TicketInfoEntity createTicketInfo(TicketInfoEntity ticketInfo) {
		if	(ticketInfo.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return ticketInfoRepository.saveAndFlush(ticketInfo);
	}

	@Override
	public TicketTimeEntity createTicketTime(TicketTimeEntity ticketTime) {
		if	(ticketTime.getID()!= 0){
			logger.warn("ID can not be set in order to be created");
			return null;
		}
		return ticketTimeRepository.saveAndFlush(ticketTime);
	}

	@Override
	public TicketEntity deleteTicketById(long id) {
		TicketEntity iObject = ticketRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		ticketRepository.delete(iObject);
		return iObject;
	}

	@Override
	public DataTemplateEntity deleteDataTemplateById(long id) {
		DataTemplateEntity iObject = dataTemplateRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		dataTemplateRepository.delete(iObject);
		return iObject;
	}

	@Override
	public EntitiesRoleEntity deleteEntitiesRoleById(long id) {
		EntitiesRoleEntity iObject = entityRoleRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		entityRoleRepository.delete(iObject);
		return iObject;
	}

	@Override
	public PhaseEntity deletePhaseById(long id) {
		PhaseEntity iObject = phaseRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		phaseRepository.delete(iObject);
		return iObject;
	}

	@Override
	public PhaseTimeEntity deletePhaseTimeById(long id) {
		PhaseTimeEntity iObject = phaseTimeRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		phaseTimeRepository.delete(iObject);
		return iObject;
	}

	@Override
	public RelationshipEntity deleteRelationshipById(long id) {
		RelationshipEntity iObject = relationshipRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		relationshipRepository.delete(iObject);
		return iObject;
	}

	@Override
	public TicketInfoEntity deleteTicketInfoById(long id) {
		TicketInfoEntity iObject = ticketInfoRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		ticketInfoRepository.delete(iObject);
		return iObject;
	}

	@Override
	public TicketTimeEntity deleteTicketTimeById(long id) {
		TicketTimeEntity iObject = ticketTimeRepository.getOne(id);
		if	(iObject == null){
			logger.warn("ID not found, delete attempt func will return a null");
			return null;
		}
		ticketTimeRepository.delete(iObject);
		return iObject;
	}
	
}
