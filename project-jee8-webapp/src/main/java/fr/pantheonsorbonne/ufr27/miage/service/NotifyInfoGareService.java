package fr.pantheonsorbonne.ufr27.miage.service;

import java.util.List;
import java.util.Map;

import fr.pantheonsorbonne.ufr27.miage.model.jaxb.Voyage;

public interface NotifyInfoGareService {

	 public void sendInfoVoyage(List<Voyage> voyagesJaxb, int time, Map<String, String> infoPerturbation);

	public void sendMessagePerturbationEtVoyages(Voyage voyage, int time);
}
