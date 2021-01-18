package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public interface NotifyInfoGareService {

	 public void sendInfoVoyage(List<Voyage> voyagesJaxb, int time);

	public void sendMessagePerturbationEtVoyages(Voyage voyage, int time);
}
