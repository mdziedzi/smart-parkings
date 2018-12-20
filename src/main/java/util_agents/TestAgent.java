package util_agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;

public class TestAgent extends Agent {
    @Override
    protected void setup() {

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL) {
            @Override
            public int onEnd() {
                System.out.println("on end");
                return super.onEnd();
            }
        };

        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("1");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                int q = 2;
                int w = 3;
                for (int i = 0; i < 10000; i++) {
                    for (int j = 0; j < 10000; j++) {
                        float e = q / w;
                    }
                }
            }
        });
        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("2");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("3");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });


        ParallelBehaviour parallelBehaviour2 = new ParallelBehaviour(this, ParallelBehaviour.WHEN_ALL) {
            @Override
            public int onEnd() {
                System.out.println("on end2");
                return super.onEnd();
            }
        };

        addBehaviour(parallelBehaviour2);
        parallelBehaviour2.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("10");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                int q = 0;
//                for (int i = 0; i < 1000; i++) {
//                    for (int j = 0; j < 1000; j++) {
//                        q++;
//                    }
//                }
            }
        });
        parallelBehaviour2.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("20");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

        parallelBehaviour2.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                System.out.println("30");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

//        addBehaviour(new CyclicBehaviour() {
//            @Override
//            public void action() {
//                System.out.println("1");
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        addBehaviour(new CyclicBehaviour() {
//            @Override
//            public void action() {
//                System.out.println("2");
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//            }
//        });
//        addBehaviour(new CyclicBehaviour() {
//            @Override
//            public void action() {
//                System.out.println("3");
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//            }
//        });

    }

    @Override
    protected void takeDown() {
        super.takeDown();
    }
}
