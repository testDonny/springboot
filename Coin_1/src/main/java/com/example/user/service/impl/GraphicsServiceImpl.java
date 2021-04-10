package com.example.user.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.user.dao.GraphicsRepository;
import com.example.user.model.GraphicsCardAll;
import com.example.user.service.GraphicsService;

@Service
public class GraphicsServiceImpl implements GraphicsService {

	@Autowired
	private GraphicsRepository graphicsRepository;
	@Autowired
	private GraphicsTX graphicsTX;

	/**
	 * 輸入資料時只要輸入8卡資料
	 * 單卡會自動儲八
	 */
	@Override
	public GraphicsCardAll insert(GraphicsCardAll graphicsCardAll) {
		GraphicsCardAll result = null;

			//8
			BigDecimal b = new BigDecimal("8");
			

			GraphicsCardAll graphicsCardAlls = new GraphicsCardAll();

			graphicsCardAlls.setName(graphicsCardAll.getName());
			
			graphicsCardAlls.setPoolcomputingpower(graphicsCardAll.getPoolcomputingpower());
			graphicsCardAlls.setPoolcomputingpower8(graphicsCardAlls.getPoolcomputingpower().divide(b, 2,
			BigDecimal.ROUND_HALF_UP));
			
			graphicsCardAlls.setCurrencyprice(graphicsCardAll.getCurrencyprice());
			
			graphicsCardAlls.setElectricity(graphicsCardAll.getElectricity());
			
			graphicsCardAlls.setElectricity8(graphicsCardAll.getElectricity().divide(b, 2,
			BigDecimal.ROUND_HALF_UP));
			
			graphicsCardAlls.setDailytheoreticalreturn(graphicsCardAll.getDailytheoreticalreturn());
			graphicsCardAlls.setDailytheoreticalreturn8(graphicsCardAll.getDailytheoreticalreturn().divide(b, 2,
			BigDecimal.ROUND_HALF_UP));
			
			graphicsCardAlls.setWholenetworkcomputingpower(graphicsCardAll.getWholenetworkcomputingpower());
			
			graphicsCardAlls.setPrice(graphicsCardAll.getPrice());
			
			graphicsCardAlls.setPowerwattage(graphicsCardAll.getPowerwattage());
//			GraphicsCardAll graphicsCardAlls = new GraphicsCardAll();
//			
//			graphicsCardAlls.setName(graphicsCardAll.getName());
//			graphicsCardAlls.setWholenetworkcomputingpower(graphicsCardAll.getWholenetworkcomputingpower());
//			graphicsCardAlls.setPoolcomputingpower(graphicsCardAll.getPoolcomputingpower());
//			graphicsCardAlls.setPoolcomputingpower8(graphicsCardAlls.getPoolcomputingpower().divide(b, 2,
//					BigDecimal.ROUND_HALF_UP));
//			graphicsCardAlls.setWholenetworkcomputingpower8(graphicsCardAlls.getWholenetworkcomputingpower().divide(b, 2,
//					BigDecimal.ROUND_HALF_UP));
//			graphicsCardAlls.setCurrencyprice(graphicsCardAll.getCurrencyprice());
//			graphicsCardAlls.setDailytheoreticalreturn(graphicsCardAll.getDailytheoreticalreturn());
//			graphicsCardAlls.setPrice(random.getRandomP());
//			graphicsCardAlls.setInstock(random.getRandom());
//			graphicsCardAlls.setElectricity(graphicsCardAll.getElectricity());
//			graphicsCardAlls.setElectricity8(graphicsCardAlls.getElectricity8());
//			result = graphicsTX.create(graphicsCardAlls);

//		for (int x = 0; x < 20; x++) {
//			BigDecimal Decimal = new BigDecimal(random.getRandomF());
//			BigDecimal Decimal2 = new BigDecimal(random.getRandomF());
//			BigDecimal Decimal3 = new BigDecimal(random.getRandomF());
//			BigDecimal Decimal4 = new BigDecimal(random.getRandomF());
//			BigDecimal Decimal5 = new BigDecimal(random.getRandomF());
//			GraphicsCardAll graphicsCardAlls = new GraphicsCardAll();
//			graphicsCardAlls.setName("顯示卡" + (x + 1));
//			graphicsCardAlls.setWholenetworkcomputingpower(Decimal2);
//			graphicsCardAlls.setPoolcomputingpower(Decimal);
//			graphicsCardAlls.setPoolcomputingpower8(graphicsCardAlls.getPoolcomputingpower().divide(b, 2,
//					BigDecimal.ROUND_HALF_UP));
//			graphicsCardAlls.setWholenetworkcomputingpower8(graphicsCardAlls.getWholenetworkcomputingpower().divide(b, 2,
//					BigDecimal.ROUND_HALF_UP));
//			graphicsCardAlls.setCurrencyprice(Decimal3);
//			graphicsCardAlls.setDailytheoreticalreturn(Decimal4);
//			graphicsCardAlls.setPrice(random.getRandomP());
//			graphicsCardAlls.setInstock(random.getRandom());
//			graphicsCardAlls.setElectricity(Decimal5);
//			graphicsCardAlls.setElectricity8(graphicsCardAlls.getElectricity8());
			result = graphicsTX.create(graphicsCardAlls);
//		}
		return result;
	}

	@Override
	public List<GraphicsCardAll> selectAll() {

		return graphicsRepository.findAll();
	}

	@Override
	public List<GraphicsCardAll> findByName(String name) {

		return graphicsRepository.findByName(name);
	}

	@Override
	public List insert(List<String> list) {
	
		return graphicsRepository.save(list);
	}

	@Override
	public GraphicsCardAll savedate(GraphicsCardAll graphicsCardAll) {

		return graphicsRepository.save(graphicsCardAll);
	}

//	@Override
//	public GraphicsCardAll instock(String name) {
//	
//		return graphicsRepository.instock(name);
//	}

}
