package main;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * Verifica se a entidade entrou em contato
     * com algum tile que possui colisão
     */
    public void checkTile(Entity entity) {
        try {
            // Posições x e y da área de colisão
            int entityLeftWorldX = entity.worldX + entity.solidArea.x;
            int entityRightWorldX = entityLeftWorldX + entity.solidArea.width;
            int entityTopWorldY = entity.worldY + entity.solidArea.y;
            int entityBottomWorldY = entityTopWorldY + entity.solidArea.height;

            // Posições das "colunas" da area de colisão
            int entityLeftCol = entityLeftWorldX / gp.TILE_SIZE;
            int entityRightCol = entityRightWorldX / gp.TILE_SIZE;

            // Posições das "linhas" da area de colisão
            int entityTopRow = entityTopWorldY / gp.TILE_SIZE;
            int entityBottomRow = entityBottomWorldY / gp.TILE_SIZE;

            // Indexes para verificar se o determinado tile possui colisão
            int tileNum1 = 0;
            int tileNum2 = 0;

            switch (entity.direction) {
                case 0: // CIMA
                    entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];

                    break;
                case 1: // BAIXO
                    entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                    break;
                case 2: // ESQUERDA
                    entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;
                    tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                    break;
                case 3: // DIREITA
                    entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;
                    tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                    break;
            }
            
            if (gp.tileManager.tiles[tileNum1].collision == true || gp.tileManager.tiles[tileNum2].collision == true) {
                entity.collisionOn = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifica se a entidade entrou em contato com algum objeto interativo
     * @param entity
     * @param player
     * @return 
     */
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                
                // Atualiza as posições x e y da área de colisão da entidade
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Atualiza as posições x e y da área de colisão do objeto
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case 0: // CIMA
                        entity.solidArea.y -= entity.speed;

                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }

                        break;
                    case 1: // BAIXO
                        entity.solidArea.y += entity.speed;

                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }

                        break;
                    case 2: // ESQUERDA
                        entity.solidArea.x -= entity.speed;

                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }

                        break;
                    case 3: // DIREITA
                        entity.solidArea.x += entity.speed;

                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }

                        break;
                }
                // Retorna os valores anteriores
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                // Retorna os valores anteriores
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index;
    }

}
